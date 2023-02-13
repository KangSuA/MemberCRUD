package member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.DBConnection;

public class MemberDAO extends DBConnection {
	public MemberDAO() {};
	public static MemberDAO getInstance() {
		return new MemberDAO();
	}
	//회원등록
	public int memberWrite(MemberDTO dto) {
		int result = 0;
		try {
			//2. 연결
			getConnection();
			
			//3. pstmt : 쿼리문 만들어 데이터 셋팅 후 실행
			sql = "insert into member(no,username,tel,email,addr) values(mem_sq.nextval,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,dto.getUsername());
			pstmt.setString(2,dto.getTel());
			pstmt.setString(3,dto.getEmail());
			pstmt.setString(4,dto.getAddr());
			
			result = pstmt.executeUpdate();
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			dbclose();
		}
		return result;
	}
	//전체회원선택
	// 전체 회원을 select -> 각 회원은 dto에 담고 -> dto는 arraylist에 담아 return.
	public List<MemberDTO> memberAll() {
		
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		
		try {
			getConnection();
			
			sql = "select no,username,tel,email,addr, to_char(writedate,'YYYY-MM-DD') writedate "
					+ "from member order by no";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt(1));
				dto.setUsername(rs.getString(2));
				dto.setTel(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setAddr(rs.getString(5));
				dto.setWritedate(rs.getString(6));
				
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbclose();
		}
		return list;
	}
	// 검색어에 해당하는 회원조회하는 메소드
	public List<MemberDTO> memberSearchSelect(String searchWord) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		try {
			getConnection();
			sql= "select no, username, tel, email, addr, to_char(writedate, 'YYYY-MM-DD') writedate from member\r\n"
					+ "where username like ? or tel like ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+searchWord+"%");
			pstmt.setString(2, "%"+searchWord+"%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setNo(rs.getInt(1));
				dto.setUsername(rs.getString(2));
				dto.setTel(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setAddr(rs.getString(5));
				dto.setWritedate(rs.getString(6));
				list.add(dto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbclose();
		}
		return list;
	}
	// 회원수정
	public int memberUpdate(String username, int item, String data) {
		int result = 0;
		try {
			getConnection();
			
			sql = "update member set ";
			if(item==1) sql+="tel";
			else if(item==2) sql+="email";
			else if(item==3) sql+="addr";
			sql+="= ? where username = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data);
			pstmt.setString(2, username);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbclose();
		}
		return result;
	}
	// 회원삭제
	public int memberDelete(int no) {
		int result=0;
		try {
			getConnection();
			
			sql = "delete from member where no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			dbclose();
		}
		return result;
	}
}
