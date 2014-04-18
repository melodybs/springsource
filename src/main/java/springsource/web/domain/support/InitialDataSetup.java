package springsource.web.domain.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import springsource.web.domain.Permission;
import springsource.web.domain.Role;
import springsource.web.domain.SpringPosts;
import springsource.web.domain.User;
import springsource.web.domain.support.EntityBuilder.EntityBuilderManager;

/**
 * Sets up initial data so the application can be used straight away. The data setup is executed in a separate
 * transaction, and committed when the {@link #setupData()} method returns
 */
public class InitialDataSetup {

    private TransactionTemplate transactionTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    
    private Role roleGuest = new Role("ROLE_GUEST");
    private Role roleAuthor = new Role("ROLE_AUTHOR");
    private Role roleAdmin = new Role("ROLE_ADMIN");
    
    private Permission permAddPost = 
    		new Permission("PERM_ADD_POST");
    private Permission permModPost =
    		new Permission("PERM_MOD_POST");
    private Permission permDelPost =
    		new Permission("PERM_DEL_POST");
    private Permission permCreateProject =
    		new Permission("PERM_CREATE_PROJECT");
    private Permission permDelProject =
    		new Permission("PERM_DEL_PROJECT");
    
    private User account;
    private SpringPosts springPosts;

    public InitialDataSetup(TransactionTemplate transactionTemplate) {
    	
        this.transactionTemplate = transactionTemplate;
    }

    public void initialize() {
    	
    	EntityBuilderManager.setEntityManager(this.entityManager);
    	
    	this.transactionTemplate.execute(new TransactionCallback<Void>() {
    		
    		@Override
    		public Void doInTransaction(TransactionStatus status) {
    			
    			if (dataIsAlreadyPresent()) {
    				
    				return null;
    			}
    			
/* create account */
    			InitialDataSetup.this.account = new UserBuilder() {
    				{
    					credentials("guest@gmail.com", "secret");
    					firstName("guest");
    					lastName("guest");
    					join_date(new Date(2001, 0, 1, 1, 11, 11));
    					roleWithPermission(InitialDataSetup.this.roleGuest);
    				}
    			}.build();

    			new UserBuilder() {
    				{
    					credentials("author@gmail.com", "secret");
    					firstName("author");
    					lastName("author");
    					join_date(new Date(2002, 1, 2, 2, 22, 22));
    					roleWithPermission(InitialDataSetup.this.roleAuthor,
    							InitialDataSetup.this.permAddPost,
    							InitialDataSetup.this.permModPost);	
    				}
    			}.build();
    			
    			new UserBuilder() {
    				{
    					credentials("admin@gmail.com", "secret");
    					firstName("admin");
    					lastName("admin");
    					join_date(new Date(2003, 2, 3, 3, 33, 33));
    					roleWithPermission(InitialDataSetup.this.roleAuthor,
    							InitialDataSetup.this.permAddPost,
    							InitialDataSetup.this.permModPost,
    							InitialDataSetup.this.permDelPost,
    							InitialDataSetup.this.permCreateProject,
    							InitialDataSetup.this.permDelProject);
    				}
    			}.build();

/* create spring post */
    			{
    				InitialDataSetup.this.springPosts = 
    						new SpringPostsBuilder() {
    					{
    						//id(1);
    						id_main(0);
    						id_prev(5);
    						id_next(2);
    						contents_ko("contents_ko01");
    						contents_en("contents_en01");
    						title("title01");
    						title_sub("title_sub01");
    						examples("http://www.naver.com01");
    						writer("melodybs01");
    					}
    				}.build();

    				new SpringPostsBuilder() {
    					{
    						//id(2);
    						id_main(1);
    						id_prev(1);
    						id_next(3);
    						contents_ko("contents_ko02");
    						contents_en("contents_en02");
    						title("title02");
    						title_sub("title_sub02");
    						examples("http://www.naver.com02");
    						writer("melodybs02");
    					}
    				}.build();
    				
    				new SpringPostsBuilder() {
    					{
    						//id(3);
    						id_main(1);
    						id_prev(2);
    						id_next(4);
    						contents_ko("contents_ko03");
    						contents_en("contents_en03");
    						title("title03");
    						title_sub("title_sub03");
    						examples("http://www.naver.com03");
    						writer("melodybs03");
    					}
    				}.build();

    				new SpringPostsBuilder() {
    					{
    						//id(4);
    						id_main(0);
    						id_prev(3);
    						id_next(5);
    						contents_ko("contents_ko04");
    						contents_en("contents_en04");
    						title("title04");
    						title_sub("title_sub04");
    						examples("http://www.naver.com04");
    						writer("melodybs04");
    					}
    				}.build();

    				new SpringPostsBuilder() {
    					{
    						//id(5);
    						id_main(4);
    						id_prev(4);
    						id_next(1);
    						contents_ko("contents_ko05");
    						contents_en("contents_en05");
    						title("title05");
    						title_sub("title_sub05");
    						examples("http://www.naver.com05");
    						writer("melodybs05");
    					}
    				}.build();
    			}
    			
    			return null;
    		}
    		
    		private boolean dataIsAlreadyPresent() {
    			
    			return InitialDataSetup.this.entityManager.createQuery(
    					"SELECT COUNT(s.id) FROM SpringPosts s", Long.class)
    							.getSingleResult().longValue() > 0;
    		}
    	});
    	
    	EntityBuilderManager.clearEntityManager();
    }
}