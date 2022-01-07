package hello.core.singleton;

// 싱글톤 방식의 주의점
public class StatefulService {
/*	
	private int price; // 상태를 유지하는 필드(공유필드) 10000 -> 20000
	
	public void order(String name, int price) {
		System.out.println("name = " + name + " price = " + price);
		this.price = price; // 여기가 문제!
	}
	
	public int getPrice() {
		return price;
	}
*/
	
	// 위에 문제 해결 방법
	public int order(String name, int price) {
		System.out.println("name = " + name + " price = " + price);
		return price;
	}
}
