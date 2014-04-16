package springsource.web.domain.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 도메인 객체 빌더 상위 클래스. 도메인 객체를 반환 전에 저장 가능.
 * {@link EntityManager} 사용. 
 */
public abstract class EntityBuilder<T extends Serializable> {
	
	protected T product;
	
	{
		initProduct();
	}
	
	//빌드 한다.
	public T build(Boolean... doNotPersist) {
		
		T product = assembleProduct();
		
		//배열이 비었거나, 비지 않고 첫번째 인자가 false라면 => TRUE 라면
		//기본값은 persist 한다.
		if (ArrayUtils.isEmpty(doNotPersist) || 
				(ArrayUtils.isNotEmpty(doNotPersist) && 
						doNotPersist[0] == Boolean.FALSE)) {
			
			EntityBuilderManager.getEntityManager().persist(product);
		}
		
		T temp = product;
		
		initProduct();
		
		return temp;
	}

	//초기화
	abstract void initProduct();
	
	//조합해 반환
	abstract T assembleProduct();
	
	/**
	 * ThreadLocal: 쓰레드 단위로 로컬 변수를 할당하는 기능. 쓰레드 영역에 변수를 설정할 수 있기 때문에, 
	 *				특정 쓰레드가 실행하는 모든 코드에서 그 쓰레드에 설정된 변수 값을 사용할 수 있게 된다. 
	 */
	public static class EntityBuilderManager {
		
		private static ThreadLocal<EntityManager> entityManagerHolder =
				new ThreadLocal<EntityManager>();
		
		public static void setEntityManager(EntityManager entityManager) {
			entityManagerHolder.set(entityManager);
		}
		
		public static void clearEntityManager() {
			entityManagerHolder.remove();
		}
		
		public static EntityManager getEntityManager() {
			return entityManagerHolder.get();
		}
	}
}
