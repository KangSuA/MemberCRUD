
public class AdminLogin {
	final String ID = "master";
	final String PWD = "1234";
	
	public static String adminId=null; // �α����� ���̵�
	private String adminPwd=null; //�α����� ���
	public static boolean logStatus=false; //�α��� ����
	public AdminLogin() {}
	public AdminLogin(String adminId, String adminPwd) {
		//�α��� (master,1234)
		if(adminId.equals(ID) && adminPwd.equals(PWD)) {
			// �α��� ����
			this.adminId = adminId;
			this.adminPwd = adminPwd;
			this.logStatus = true;
		}else {
			//�α��� ����
			this.adminId = null;
			this.adminPwd = null;
			this.logStatus = false;
		}
	}
	public void logout() {
		// �α��ν� ������ ���� �ʱ�ȭ
		adminId = null;
		adminPwd = null;
		logStatus = false;
	}
}
