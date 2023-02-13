import java.util.List;
import java.util.Scanner;

import member.MemberDAO;
import member.MemberDTO;

public class MemberStart {

	public MemberStart() {
		
	}
	public void start() {
		do {
			// 관리자 로그인 (아이디, 비밀번호를 입력)
			System.out.println("-----관리자 로그인-----");
			String adminId = conInput("관리자 아이디");
			String adminPwd = conInput("관리자 비밀번호");
			
			AdminLogin login = new AdminLogin(adminId, adminPwd);
			while(AdminLogin.logStatus) { // 로그인 됨
				// 회원정보처리 메뉴
				int menu = menuOutput();
				switch(menu) {
				case 1: // 전체회원목록
					memberList();
					break;
				case 2: // 회원등록
					memberInsert();
					break;
				case 3: // 회원정보수정
					memberEdit();
					break;
				case 4: // 회원삭제
					memberDel();
					break;
				case 5: // 회원검색
					memberSearch();
					break;
				case 6: //로그아웃
					login.logout();
					break;
				case 7: // 종료
					System.out.println("회원관리 프로그램이 종료되었습니다.");
					System.exit(0);
					break;
				default:
					System.out.println("메뉴를 잘못입력하였습니다.");
				}
			}
		}while(true);
	}
	// 회원삭제
	public void memberDel() {
		memberSearch();
		int no = Integer.parseInt(conInput("삭제할 회원번호"));
		MemberDAO dao = MemberDAO.getInstance();
		int result = dao.memberDelete(no);
		if(result>0) {
			System.out.println(no+"번회원이 삭제되었습니다.");
		}else {
			System.out.println(no+"번회원을 삭제하지 못했습니다.");
		}
	}
	// 회원수정
	public void memberEdit() {
		//수정할 회원명
		String username = conInput("수정할 회원명");
		// 수정할 항목(연락처, 이메일, 주소중에 택1)
		int item = Integer.parseInt(conInput("수정할 필드 (1)연락처 (2)이메일 (3)주소"));
		// 수정할 값
		String editData = conInput("수정할 데이터");
		
		MemberDAO dao = MemberDAO.getInstance();
		int result = dao.memberUpdate(username, item, editData);
		if(result>0) {
			System.out.println("회원정보가 수정되었습니다.");
		}else {
			System.out.println("회원정보 수정 실패하였습니다.");
		}
	}
	// 회원검색
	public void memberSearch() {
		String searchWord = conInput("검색할 회원명 or 전화번호");
		MemberDAO dao = MemberDAO.getInstance();
		List<MemberDTO> serachList = dao.memberSearchSelect(searchWord);
		memberPrint(serachList);
	}
	// 전체회원선택
	public void memberList() {
		MemberDAO dao = MemberDAO.getInstance();
		List<MemberDTO> list = dao.memberAll();
		
		memberPrint(list);
	}
	// 회원 출력
	public void memberPrint(List<MemberDTO> list) {
		System.out.printf("%6s %10s %15s %20s %20s %10s\n", "번호","회원명","연락처","이메일","주소","등록일");
		for(int i=0; i<list.size(); i++) {
			MemberDTO dto = list.get(i);
			System.out.printf("%6d %10s %15s %20s %20s %10s\n",dto.getNo(),dto.getUsername(),
					dto.getTel(),dto.getEmail(),dto.getAddr(),dto.getWritedate());
		}
	}
	// 회원 등록
	public void memberInsert() {
		// 회원명, 회원연락처, 이메일, 주소를 입력받아 DTO에 담는다.
		String username = conInput("회원명");
		String tel = conInput("연락처(ex:010-1234-5678)");
		String email = conInput("이메일");
		// 이메일 유효성 검사
		String addr = conInput("주소");
		
		/*MemberDTO dto = new MemberDTO();
		dto.setUsername(username);
		dto.setTel(tel);
		dto.setEmail(email);
		dto.setAddr(addr);*/
		MemberDTO dto = new MemberDTO(username,tel,email,addr);
		
		MemberDAO dao = MemberDAO.getInstance();
		
		int result = dao.memberWrite(dto); //!0-> 회원등록됨. 0-> 회원등록안됨
		if(result>0) {
			System.out.println("회원이 등록되었습니다.");
		}else {
			System.out.println("회원등록 실패하였습니다.");
		}
	}
	
	// 메뉴 표시
	public int menuOutput() {
		Scanner scan = new Scanner(System.in);
		int menu = 0;
		try {
			System.out.print("(1)전체회원목록, (2)회원등록, (3)회원수정, (4)회원삭제, (5)회원검색, (6)로그아웃, (7)종료 ->");
			menu = Integer.parseInt(scan.nextLine());
			
		}catch(NumberFormatException nfe) {
			System.out.println("메뉴는 숫자이어야 합니다.");
		}
		return menu;
	}
	
	// 콘솔에서 데이터 입력받는 메소드
	public String conInput(String title) {
		Scanner scan = new Scanner(System.in);
		System.out.print(title+"->");
		return scan.nextLine();
	}
	
	public static void main(String[] args) {
		MemberStart ms = new MemberStart();
		ms.start();
	}

}
