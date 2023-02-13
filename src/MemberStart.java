import java.util.List;
import java.util.Scanner;

import member.MemberDAO;
import member.MemberDTO;

public class MemberStart {

	public MemberStart() {
		
	}
	public void start() {
		do {
			// ������ �α��� (���̵�, ��й�ȣ�� �Է�)
			System.out.println("-----������ �α���-----");
			String adminId = conInput("������ ���̵�");
			String adminPwd = conInput("������ ��й�ȣ");
			
			AdminLogin login = new AdminLogin(adminId, adminPwd);
			while(AdminLogin.logStatus) { // �α��� ��
				// ȸ������ó�� �޴�
				int menu = menuOutput();
				switch(menu) {
				case 1: // ��üȸ�����
					memberList();
					break;
				case 2: // ȸ�����
					memberInsert();
					break;
				case 3: // ȸ����������
					memberEdit();
					break;
				case 4: // ȸ������
					memberDel();
					break;
				case 5: // ȸ���˻�
					memberSearch();
					break;
				case 6: //�α׾ƿ�
					login.logout();
					break;
				case 7: // ����
					System.out.println("ȸ������ ���α׷��� ����Ǿ����ϴ�.");
					System.exit(0);
					break;
				default:
					System.out.println("�޴��� �߸��Է��Ͽ����ϴ�.");
				}
			}
		}while(true);
	}
	// ȸ������
	public void memberDel() {
		memberSearch();
		int no = Integer.parseInt(conInput("������ ȸ����ȣ"));
		MemberDAO dao = MemberDAO.getInstance();
		int result = dao.memberDelete(no);
		if(result>0) {
			System.out.println(no+"��ȸ���� �����Ǿ����ϴ�.");
		}else {
			System.out.println(no+"��ȸ���� �������� ���߽��ϴ�.");
		}
	}
	// ȸ������
	public void memberEdit() {
		//������ ȸ����
		String username = conInput("������ ȸ����");
		// ������ �׸�(����ó, �̸���, �ּ��߿� ��1)
		int item = Integer.parseInt(conInput("������ �ʵ� (1)����ó (2)�̸��� (3)�ּ�"));
		// ������ ��
		String editData = conInput("������ ������");
		
		MemberDAO dao = MemberDAO.getInstance();
		int result = dao.memberUpdate(username, item, editData);
		if(result>0) {
			System.out.println("ȸ�������� �����Ǿ����ϴ�.");
		}else {
			System.out.println("ȸ������ ���� �����Ͽ����ϴ�.");
		}
	}
	// ȸ���˻�
	public void memberSearch() {
		String searchWord = conInput("�˻��� ȸ���� or ��ȭ��ȣ");
		MemberDAO dao = MemberDAO.getInstance();
		List<MemberDTO> serachList = dao.memberSearchSelect(searchWord);
		memberPrint(serachList);
	}
	// ��üȸ������
	public void memberList() {
		MemberDAO dao = MemberDAO.getInstance();
		List<MemberDTO> list = dao.memberAll();
		
		memberPrint(list);
	}
	// ȸ�� ���
	public void memberPrint(List<MemberDTO> list) {
		System.out.printf("%6s %10s %15s %20s %20s %10s\n", "��ȣ","ȸ����","����ó","�̸���","�ּ�","�����");
		for(int i=0; i<list.size(); i++) {
			MemberDTO dto = list.get(i);
			System.out.printf("%6d %10s %15s %20s %20s %10s\n",dto.getNo(),dto.getUsername(),
					dto.getTel(),dto.getEmail(),dto.getAddr(),dto.getWritedate());
		}
	}
	// ȸ�� ���
	public void memberInsert() {
		// ȸ����, ȸ������ó, �̸���, �ּҸ� �Է¹޾� DTO�� ��´�.
		String username = conInput("ȸ����");
		String tel = conInput("����ó(ex:010-1234-5678)");
		String email = conInput("�̸���");
		// �̸��� ��ȿ�� �˻�
		String addr = conInput("�ּ�");
		
		/*MemberDTO dto = new MemberDTO();
		dto.setUsername(username);
		dto.setTel(tel);
		dto.setEmail(email);
		dto.setAddr(addr);*/
		MemberDTO dto = new MemberDTO(username,tel,email,addr);
		
		MemberDAO dao = MemberDAO.getInstance();
		
		int result = dao.memberWrite(dto); //!0-> ȸ����ϵ�. 0-> ȸ����Ͼȵ�
		if(result>0) {
			System.out.println("ȸ���� ��ϵǾ����ϴ�.");
		}else {
			System.out.println("ȸ����� �����Ͽ����ϴ�.");
		}
	}
	
	// �޴� ǥ��
	public int menuOutput() {
		Scanner scan = new Scanner(System.in);
		int menu = 0;
		try {
			System.out.print("(1)��üȸ�����, (2)ȸ�����, (3)ȸ������, (4)ȸ������, (5)ȸ���˻�, (6)�α׾ƿ�, (7)���� ->");
			menu = Integer.parseInt(scan.nextLine());
			
		}catch(NumberFormatException nfe) {
			System.out.println("�޴��� �����̾�� �մϴ�.");
		}
		return menu;
	}
	
	// �ֿܼ��� ������ �Է¹޴� �޼ҵ�
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
