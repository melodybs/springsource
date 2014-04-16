package springsource.web.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import springsource.web.config.TestDataContextConfiguration;
import springsource.web.config.TestPersistenceConfiguration;
import springsource.web.domain.SpringPosts;
import springsource.web.domain.support.EntityBuilder.EntityBuilderManager;
import springsource.web.service.SpringPostsService;

/**
 * 아이디    1 2 3 4 5  
 * 기본상태  M s s M s  (M=메인글, s=서브글)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { 
		TestPersistenceConfiguration.class, 
		TestDataContextConfiguration.class })
@Transactional
public class SpringPostsServiceTest {

	@Autowired
	private SpringPostsService springPostsService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//테스트용
	private SpringPosts targetPost = new SpringPosts();
	private SpringPosts springPost = new SpringPosts();
	private SpringPosts originPrevPost = new SpringPosts();
	private SpringPosts originNextPost = new SpringPosts();
	private SpringPosts newPrevPost = new SpringPosts();
	private SpringPosts newNextPost = new SpringPosts();
	
	private List<SpringPosts> springPostsList = new ArrayList<SpringPosts>();
	
	private int result;
	private int len;
	
	@Before
	public void setupData() {
		
		EntityBuilderManager.setEntityManager(entityManager);
		
		/*SpringPosts targetPost = new SpringPostsBuilder() {
			{
				//id(6);
				id_main();
				id_prev(2);
				id_next(3);
				contents_ko("targetPost_ko01");
				contents_en("targetPost_en01");
				title("targetPost_title01");
				title_sub("targetPost_sub01");
				examples("http://www.targetPost.com01");
				writer("targetPost_melodybs01");
			}
		}.build();*/
	}
	
	@Test
	public void testfindSpringPostById() {
		
		entityManager.flush();
		
		springPost = springPostsService.findSpringPostById(1);
		
		assertSame(springPost.getId(), 1L);
		assertSame(springPost.getId_main(), 0L);
		assertSame(springPost.getId_prev(), 5L);
		assertSame(springPost.getId_next(), 2L);
		assertEquals(springPost.getTitle(), "title01");
		assertEquals(springPost.getTitle_sub(), "title_sub01");
		assertEquals(springPost.getWriter(), "melodybs01");
		assertEquals(springPost.getExamples(), "http://www.naver.com01");
		assertEquals(springPost.getContents_ko(), "contents_ko01");
		assertEquals(springPost.getContents_en(), "contents_en01");
	}
	
	@Test
	public void testFindMainTitles() {
		
		setupData();
		
		springPostsList = this.springPostsService.findMainPosts();
		len = springPostsList.size();
		
		assertSame(len, 2);
		assertSame(springPostsList.get(0).getId(), 1L);
		assertSame(springPostsList.get(1).getId(), 4L);
	}
	
	@Test
	public void testFindAllTitles() {
		
		setupData();
		
		/*List<SpringPosts> springPostsList = springPostsService
				.findAllPosts();
		int len = springPostsList.size();
		
		assertSame(len, 5);
		assertSame(springPostsList.get(0).getId(), 1L);
		assertSame(springPostsList.get(1).getId(), 2L);
		assertSame(springPostsList.get(2).getId(), 3L);
		assertSame(springPostsList.get(3).getId(), 4L);
		assertSame(springPostsList.get(4).getId(), 5L);*/
		
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
	}
	
	@Test
	public void testFindPossiblePrevPosts() {
		
		//MssMs
		setupData();
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});

		springPostsList = this.springPostsService.findPossiblePrevPosts(0L);
		len = springPostsList.size();
		assertSame(len, 2);
		assertSame(springPostsList.get(0).getId(), 3L);
		assertSame(springPostsList.get(1).getId(), 5L);

		springPostsList = this.springPostsService.findPossiblePrevPosts(1L);
		len = springPostsList.size();
		assertSame(len, 3);
		assertSame(springPostsList.get(0).getId(), 1L);
		assertSame(springPostsList.get(1).getId(), 2L);
		assertSame(springPostsList.get(2).getId(), 3L);
		
	}
	
	@Test
	public void testdeleteSpringPost() {
		
		setupData();
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		
		assertNotNull(springPostsService.findSpringPostById(3));
		assertSame(springPostsService.findSpringPostById(2)
				.getId_next(), 3L);
		assertSame(springPostsService.findSpringPostById(4)
				.getId_prev(), 3L);
		
		//delete complete //Ms[s]Ms -> MsMs
		targetPost = this.springPostsService.findSpringPostById(3L);
		result = springPostsService.deleteSpringPost(targetPost);
		currentState(4, new long[] {1L, 2L, 4L, 5L});
		pTest(result, 0, false, new long[] {3L, 2L, 4L}, 
				new long[] {4L, 2L});
		
		//첫번째글 삭제 불가 //MsMs
		targetPost = this.springPostsService.findSpringPostById(1L);
		result = springPostsService.deleteSpringPost(targetPost);
		currentState(4, new long[] {1L, 2L, 4L, 5L});
		pTest(result, 1, true, new long[] {1L}, 
				new long[] {1L, 0L, 5L, 2L});
		
		//메인글 삭제시 서브글 같이 삭제. 포인터 확인. //Ms[M]s -> Ms
		targetPost = this.springPostsService.findSpringPostById(4L);
		result = springPostsService.deleteSpringPost(targetPost);
		currentState(2, new long[] {1L, 2L});
		pTest(result, 0, false, new long[] {4L, 2L, 1L}, 
				new long[] {1L, 2L});
		pTest(result, 0, false, new long[] {5L}, 
				new long[] {});
				
		//마지막글 삭제시에 첫번째글의 이전글 확인 //M[s] -> M
		targetPost = this.springPostsService.findSpringPostById(2L);
		result = springPostsService.deleteSpringPost(targetPost);
		
		currentState(1, new long[] {1L});
		assertSame(result, 0);
		assertNull(springPostsService.findSpringPostById(2));
		assertSame(springPostsService.findSpringPostById(1)
				.getId_next(), 1L);
		assertSame(springPostsService.findSpringPostById(1)
				.getId_prev(), 1L);
		
		pTest(result, 0, false, new long[] {2L, 1L, 1L}, 
				new long[] {1L, 1L});
	}
	
	@Test
	public void teststoreSpringPost() {
		
		setupData();
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		
		//메인글을 서브글 사이에 등록하려는 경우.
		//등록된다면 글의 아이디: 6 //MssMs
		setPost(targetPost, 0L, 2L);
		targetPost.setTitle("");
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 1, false, new long[] {6L, 2L, 3L}, 
				new long[] {3L, 2L});
		
		//메인글을 앞글이 메인글, 뒷글이 서브글사이에 등록할경우
		//등록된다면 글의 아이디: 6 //MssMs
		setPost(targetPost, 0L, 1L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 1, false, new long[] {6L, 1L, 2L}, 
				new long[] {2L, 1L});
		
		//서브글을 다른 서브글들 사이에 등록하려고 하는경우.
		//등록된다면 글의 아이디: 6 //MssMs
		setPost(targetPost, 4L, 2L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 2, false, new long[] {6L, 2L, 3L}, 
				new long[] {3L, 2L});
		
		//서브글을 다른서브글과 메인글 사이에 넣으려고 하는 경우
		//등록된다면 글의 아이디: 6 //MssMs
		setPost(targetPost, 1L, 4L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 2, false, new long[] {6L, 4L, 5L}, 
				new long[] {5L, 4L});
		
		//서브글들 사이에 정상적으로 글쓰기
		//등록글 아이디: 6 //Ms[s]sMs
		setPost(targetPost, 1L, 2L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(6, new long[] {1L, 2L, 6L, 3L, 4L, 5L});
		pTest(result, 0, true, new long[] {6L, 2L, 3L}, 
				new long[] {6L, 1L, 2L, 3L, 6L, 6L});
		
		//마지막글로 등록
		//위에 테스트 성공 가정시 등록글 아이디: 7 //MsssMs[M]
		setPost(targetPost, 0L, 5L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(7, new long[] {1L, 2L, 6L, 3L, 4L, 5L, 7L});
		pTest(result, 0, true, new long[] {7L, 5L, 1L}, 
				new long[] {7L, 0L, 5L, 1L, 7L, 7L});
		
		//메인글과 메인글 사이에 메인글 등록
		//위에 테스트 성공 가정시 등록글 아이디: 8 //MsssMsM[M]
		setPost(targetPost, 0L, 7L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(8, new long[] {1L, 2L, 6L, 3L, 4L, 5L, 7L, 8L});
		pTest(result, 0, true, new long[] {8L, 7L, 1L}, 
				new long[] {8L, 0L, 7L, 1L, 8L, 8L});
		
		//메인글 메인글 사이에 서브글 등록
		//위에 테스트 성공 가정시 등록글 아이디: 9 //MsssMsMM[s]
		setPost(targetPost, 8L, 8L);
		result = this.springPostsService.storeSpringPost(targetPost);
		currentState(9, new long[] {1L, 2L, 6L, 3L, 4L, 5L, 7L, 8L, 9L});
		pTest(result, 0, true, new long[] {9L, 8L, 1L}, 
				new long[] {9L, 8L, 8L, 1L, 9L, 9L});
	}
	
	@Test
	public void testmodifySpringPost() {
		 
		setupData();
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		
		//위치 변경 없이 데이터만 변경.
		//MssMs
		setPost(targetPost, 0L, 5L, 1L, 2L);
		targetPost.setContents_ko("modify_ko");
		targetPost.setContents_en("modify_en");
		targetPost.setExamples("modify_examples");
		targetPost.setTitle("modify_title");
		targetPost.setTitle_sub("modify_title_sub");
		targetPost.setWriter("modify_writer");
		result = this.springPostsService.modifySpringPost(targetPost);
		springPost = this.springPostsService.findSpringPostById(1L);
		
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 0, true, new long[] {1L}, new long[] {1L, 0L, 5L, 2L});
		assertEquals(springPost.getContents_ko(), "modify_ko");
		assertEquals(springPost.getContents_en(), "modify_en");
		assertEquals(springPost.getExamples(), "modify_examples");
		assertEquals(springPost.getTitle(), "modify_title");
		assertEquals(springPost.getTitle_sub(), "modify_title_sub");
		assertEquals(springPost.getWriter(), "modify_writer");

		//메인글 이동 불가
		//MssMs
		setPost(targetPost, 0L, 2L, 1L, 2L);
		result = this.springPostsService.modifySpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 1, true, new long[] {1L}, new long[] {1L, 0L, 5L, 2L});
		
		//서브글이지만 변경 불가능한위치	
		//MssMs
		setPost(targetPost, 4L, 3L, 5L);
		result = this.springPostsService.modifySpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 2, true, new long[] {5L}, new long[] {5L, 4L, 4L, 1L});
		
		//서브글이고 원래 변경 불가능한 위치이지만 메인글을 변경함으로써 가능해진 경우.		
		//MssM[s] -> Ms[s]sM
		setPost(targetPost, 1L, 2L, 5L);
		result = this.springPostsService.modifySpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 5L, 3L, 4L});
		pTest(result, 0, true, new long[] {5L, 4L, 1L, 2L, 3L}, 
				new long[] {5L, 1L, 2L, 3L, 1L, 4L, 5L, 5L});
		
		//서브글 이동		
		//Ms[s]sM -> MssM[s]
		setPost(targetPost, 4L, 4L, 5L);
		result = this.springPostsService.modifySpringPost(targetPost);
		currentState(5, new long[] {1L, 2L, 3L, 4L, 5L});
		pTest(result, 0, true, new long[] {5L, 2L, 3L, 4L, 1L}, 
				new long[] {5L, 4L, 4L, 1L, 3L, 2L, 5L, 5L});
	}
	
	@Test
	public void testfindNextMainSpringPosts() {
		
		//[M]ssMs
		springPost = this.springPostsService.findNextMainSpringPost(1L);
		assertSame(springPost.getId(), 4L);
		
		//Mss[M]s
		springPost = this.springPostsService.findNextMainSpringPost(4L);
		assertSame(springPost.getId(), 1L);
	
		//M[s]sMs
		springPost = this.springPostsService.findNextMainSpringPost(2L);
		assertNull(springPost);
	}
	
	@After
	public void tearDown() {
		
		EntityBuilderManager.clearEntityManager();
	}
	
	//현재 포스트의 개수, 아이디 점검
	private void currentState(int len, long[] expectedArr) {
		
		List<SpringPosts> springPostsList = 
				springPostsService.findAllPosts();
		int realLen = springPostsList.size();
		
		assertSame(realLen, len);
		
		for (int i = 0; i < len; i++) {
			
			assertSame(springPostsList.get(i).getId(), expectedArr[i]);
		}
	}
	
	/**
	 * 표스트 이동후의 위치및 포인터 점검
	 * 
	 * 
	 * result: real result
	 * exResult: expect result
	 * expectArr
	 * ----Notnull
	 * 	   expectArr[0]: targetPost          id
	 * 	   expectArr[1]: targetPost     main id
	 *     expectArr[2]: targetPost     prev id
	 *     expectArr[3]: targetPost     next id
	 *     expectArr[4]: originPrevPost next id
	 *     expectArr[5]: originNextPost prev id
	 *     expectArr[6]: newPrevPost    next id
	 *     expectArr[7]: newNextPost    prev id
	 * ----Null
	 * 	   expectArr[0]: originPrevPost next id
	 *     expectArr[1]: originNextPost prev id
	 *     expectArr[2]: newPrevPost    next id
	 *     expectArr[3]: newNextPost    prev id
	 * postArr
	 * 	   postsArr[0]: targetPost id
	 *     postsArr[1]: originPrevPost id
	 *     postsArr[2]: originNextPost id
	 *     postsArr[3]: newPrevPost id
	 *     postsArr[4]: newNextPost id
	 * targetPost: target(change) post
	 * 
	 * @param result
	 * @param exResult
	 * @param expectArr
	 * @param postArr
	 */
	private void pTest(int result, int exResult, boolean notNull, 
			long[] postArr, long[] expectArr) {
		
		int postLen = postArr.length;
		//int expectLen = expectArr.length;
		
		springPost = this.springPostsService.findSpringPostById(postArr[0]);
		
		if (notNull) {
			
			assertSame(result, exResult);
			assertNotNull(springPost);
			assertSame(springPost.getId(), expectArr[0]);
			assertSame(springPost.getId_main(), expectArr[1]);
			assertSame(springPost.getId_prev(), expectArr[2]);
			assertSame(springPost.getId_next(), expectArr[3]);
			
			if (postLen > 1) {
				
				originPrevPost = this.springPostsService
						.findSpringPostById(postArr[1]);
				originNextPost = this.springPostsService
						.findSpringPostById(postArr[2]);
				
				assertSame(originPrevPost.getId_next(), expectArr[4]);
				assertSame(originNextPost.getId_prev(), expectArr[5]);
				
				if (postLen > 3) {
					
					newPrevPost = this.springPostsService
							.findSpringPostById(postArr[3]);
					newNextPost = this.springPostsService
							.findSpringPostById(postArr[4]);
					
					assertSame(newPrevPost.getId_next(), expectArr[6]);
					assertSame(newNextPost.getId_prev(), expectArr[7]);
				}
			}
			
		} else {
			
			assertSame(result, exResult);
			assertNull(springPost);
			
			if (postLen > 1) {
				
				originPrevPost = this.springPostsService
						.findSpringPostById(postArr[1]);
				originNextPost = this.springPostsService
						.findSpringPostById(postArr[2]);
				
				assertSame(originPrevPost.getId_next(), expectArr[0]);
				assertSame(originNextPost.getId_prev(), expectArr[1]);
				
				/*if (postLen > 3) {
					
					newPrevPost = this.springPostsService
							.findSpringPostById(postArr[3]);
					newNextPost = this.springPostsService
							.findSpringPostById(postArr[4]);
					
					assertSame(newPrevPost.getId_next(), expectArr[2]);
					assertSame(newNextPost.getId_prev(), expectArr[3]);
				}*/
			}
		}
	}
	
	/**
	 * attrArr[0]: id_main
	 * attrArr[1]: id_prev
	 * attrArr[3]: id
	 * attrArr[2]: id_next
	 * 
	 * @param targetPost
	 * @param id
	 * @param id_main
	 * @param id_prev
	 */
	private void setPost(SpringPosts targetPost, long... attrArr) {

		int len = attrArr.length;
		
		targetPost.setId_main(attrArr[0]);
		targetPost.setId_prev(attrArr[1]);
		
		//main id exist
		if (len > 2) {
			
			targetPost.setId(attrArr[2]);
			
			//next id exist
			if (len == 4) {
				
				targetPost.setId_next(attrArr[3]);
			}
		}
	}
}
