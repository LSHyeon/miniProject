package com.accountbook.jdbc;

import java.util.List;
import java.util.Scanner;

public class PlayAccount_Book {
	Scanner sc = new Scanner(System.in);
	MemberVO mvo = new MemberVO();
	MemberDAO dao = new MemberDAO();
	
	public void SignUpMember() { // 회원가입
		System.out.println("사용할 아이디를 입력해 주세요: ");
		String id = sc.next();

		System.out.println("사용할 비밀번호를 입력해 주세요: ");
		String password = sc.next();

		System.out.println("이름을 입력해 주세요: ");
		String name = sc.next();
		
		System.out.println("주민번호를 입력해 주세요: ");
		String jumin = sc.next();

		System.out.println("핸드폰 번호를 입력해 주세요: ");
		String phone = sc.next();

		System.out.println("이메일을 입력해 주세요: ");
		String email = sc.next();

		mvo = new MemberVO(id, password, name, jumin, phone, email);
		int result = dao.InsertData(mvo);
		if (result == 1)
			System.out.println(id + "님 회원가입이 완료 되었습니다");

		mvo = dao.selectId(id);
		System.out.println("회원 정보: " + mvo);
	}
	
	public void loginMember() {	// 로그인
		System.out.println("아이디를 입력해 주세요: ");
		String id = sc.next();

		System.out.println("비밀번호를 입력해주세요: ");
		String password = sc.next();

		int result = dao.login(id, password);

		while (true) { // 작업시작
			if (result == 1) {
				mvo = dao.selectId(id);
				System.out.println("원하는 작업을 선택해주세요(1.카드관련, 2.입/출내역관련, 3.계정관련, 0.초기화면)");
				System.out.println(">>");
				int num2 = sc.nextInt();

				if (num2 == 1) {
					manageCard(mvo);
			
				} else if (num2 == 2) {
					manageAccount(mvo);
					
				} else if (num2 == 3) {
					managerUser(mvo);
					
				} else if (num2 == 0) {
					System.out.println("초기 화면으로 돌아갑니다");
					break;
				}
			} else {
				System.out.println("초기 화면으로 돌아갑니다");
				return;
			}
		}
	}
	
	public void managerUser(MemberVO member) {
		String id = mvo.getId();
		
		System.out.println("(1.계정 수정, 2.회원 탈퇴)");
		System.out.println(">>");
		int uNum = sc.nextInt();
		
		if(uNum == 1) {
			System.out.println("계정 수정작업 페이지입니다");
			System.out.println("=====현재 계정 정보=====");
			System.out.println(mvo);
			
			if (mvo == null) {
				System.out.println("=====현재 계정 정보=====");
				mvo = dao.selectId(id);
				System.out.println("없는 아이디 입니다");
				return;
			}
			
			System.out.println("===== 계정 수정 =====");
			
			String jumin = mvo.getJumin();
			
			String id2 = mvo.getId();
			
			System.out.println("수정할 비밀번호:");
			String password = sc.next();
			if (password.trim().equals(""))
				password = mvo.getPassword();
			
			System.out.println("수정할 이름:");
			String name = sc.next();
			if (name.trim().equals(""))
				name = mvo.getName();
			
			System.out.println("수정할 핸드폰:");
			String phone = sc.next();
			if (phone.trim().equals(""))
				phone = mvo.getPhone();
			
			System.out.println("수정할 이메일:");
			String email = sc.next();
			if (email.trim().equals(""))
				email = mvo.getEmail();
		
		mvo = new MemberVO(id2, password, name, jumin, phone, email);
		dao.UpdateMember(mvo);
		System.out.println("수정된 계정: " + mvo);

		}
	
		if (uNum == 2) {
			System.out.println("회원 탈퇴 페이지입니다");
			System.out.println("주민번호 입력시 계정 탈퇴가 완료됩니다");
			System.out.println("주민번호를 입력해주세요: ");
			String jumin = sc.next();

			if (!(jumin.trim().equals(mvo.getJumin()))) {
				System.out.println("주민번호가 일치하지 않습니다");
				return;
			}
			
				dao.DeleteMember(jumin);
				System.out.println(id + "계정이 삭제되었습니다");
		}
	}
	
	public void manageCard(MemberVO member) { // 카드 관련 작업
		String id;
		int result;
		
		System.out.println("(1.카드생성, 2.등록한 카드조회, 3.등록한 카드수정, 4.등록한 카드삭제, 5.카드사용 내역, 0.이전 화면)");
		System.out.println(">>");
		int cNum = sc.nextInt();

		if (cNum == 1) {
			System.out.println("카드생성 메뉴입니다");

			id = mvo.getId();

			System.out.println("카드사 입력: ");
			String card = sc.next();

			System.out.println("카드번호 입력: ");
			String cardNum = sc.next();
			
			System.out.println("카드별칭 입력: ");
			String cardNick = sc.next();

			mvo.CardMember(id, card, cardNum, cardNick);
			result = dao.CardInsert(mvo);
			if (result == 1)
				System.out.println(id + "님 카드등록이 완료 되었습니다");

			mvo.cardInfo();
		}

		if (cNum == 2) { // 카드 조회
			System.out.println("카드를 조회합니다");

			System.out.println("아이디를 입력해주세요: ");
			id = sc.next();
			
			List<MemberVO> list = dao.cardSelectAll(id);
			for (MemberVO mvo : list) {
				mvo.cardInfo();
			}
		}
		
		if (cNum == 3) { // 카드 정보 수정
			System.out.println("카드정보를 수정합니다");

			System.out.println("조회할 아이디를 입력해주세요: ");
			id = sc.next();

			mvo = dao.selectId(id);
			if (mvo == null) {
				System.out.println("없는 아이디 입니다");
				return;
			}
			
			System.out.println(">> 수정 가능한 " + id + "님의 카드 목록입니다");
			List<MemberVO> list = dao.cardSelectAll(id);
			for (MemberVO mvo2 : list)
				mvo2.cardInfo();

			System.out.println("===== 카드 수정 =====");
			System.out.println("수정할 카드의 카드번호를 입력해주세요: ");
			String cardNum = sc.next();
			
			List<MemberVO> list2 = dao.cardSelectAll(id);
			
			for (MemberVO mvo2 : list2) {
				if (!cardNum.trim().equals("") && cardNum.equals(mvo2.getCardNum())) {
					mvo.setCardNum(cardNum);
					System.out.println("수정할 작업 선택(1.카드사 수정, 2.카드별칭 수정, 0.이전 화면)");
					System.out.println(">>");
					int upNum = sc.nextInt();

					if (upNum == 1) {
						String card = inputString("카드사");
						mvo2.setCard(card);
					} else if (upNum == 2) {
						String cardNick = inputString("카드 별칭");
						mvo2.setCardNick(cardNick);
					} else if (upNum == 0) {
						System.out.println("이전화면으로 돌아갑니다");
						return;
					}

					mvo2.cardInfo();
					dao.cardUpdate(mvo2);
				}
			}// end of for
		} // end of if(cNum == 3)
		
		if (cNum == 4) { // 카드 삭제
			System.out.println("카드정보를 삭제합니다");
			
			System.out.println("조회할 아이디를 입력해주세요: ");
			id = sc.next();

			mvo = dao.selectId(id);
			if (mvo == null) {
				System.out.println("없는 아이디 입니다");
				return;
			}
			
			List<MemberVO> list = dao.cardSelectAll(id);
			for (MemberVO mvo2 : list)
				mvo2.cardInfo();
			
			System.out.println("===== 카드 삭제 =====");
			System.out.println("삭제할 카드의 카드번호를 입력해 주세요: ");
			String cardNum = sc.next();
			
			result = dao.DeleteCard(cardNum);
			System.out.println(cardNum + " 카드를 삭제하였습니다");
			
		} // end of if(cNum == 4)
		
		if (cNum == 5) { // 카드 사용 내역 조회
			manageCardInOut(member);
		}
		
		if(cNum == 0) {
			System.out.println("이전화면으로 돌아갑니다");
				return;
		}
	}
	
	public void manageAccount(MemberVO member) { // 입/출내역 관리
		String id;
		int result;
		
		System.out.println("(1.입/출내역 등록, 2.입/출내역 조회, 3.입/출내역 수정, 4.입/출내역 삭제, 0.이전화면)");
		System.out.println(">>");
		int aNum = sc.nextInt();

		if (aNum == 1) { // 입/출내역 등록
			System.out.println("입/출내역 등록 페이지입니다");
			
			int accNo = mvo.getAccNo();
			
			id = mvo.getId();
			
			System.out.println("(입/출)구분 입력: ");
			String accInOut = sc.next();

			System.out.println("입/출금액 입력(숫자만 입력): ");
			int inOutMoney = sc.nextInt();
			
			System.out.println("날짜 입력(YY/MM/DD): ");
			String hiredate = sc.next();
			
			System.out.println("※카테고리 ex) [자기계발] [생활비] [식대] [교육] [문화] [미용] [교통] [쇼핑] [월급]");
			System.out.println("카테고리: ");
			String category = sc.next();
			
			mvo.accMember(accNo, id, accInOut, inOutMoney, hiredate, category );
			result = dao.AccountInsert(mvo);
			if (result == 1)
				System.out.println(id + "님 입/출내역 등록이 완료 되었습니다");

			mvo.inOutAccInfo();
		}

		if (aNum == 2) { // 입/출내역 조회
			System.out.println("입/출내역을 조회합니다");
			System.out.println("(1.날짜별 조회, 2.잔액 조회, 3.입/출내역 전체 출력, 0.이전화면)");
			int search = sc.nextInt();
			
			if (search == 1) {
				System.out.println("조회할 아이디를 입력해주세요: ");
				id = sc.next();

				System.out.println("조회할 날짜(YY/MM/DD)를 입력해 주세요: ");
				String hiredate = sc.next();

				if (id.equals(mvo.getId())) {
					List<MemberVO> list = dao.AccountSelect(id, hiredate);
					for (MemberVO mvo : list)
						mvo.inOutAccInfo();
				}
			}

			if (search == 2) {
				System.out.println("조회할 아이디를 입력해주세요: ");
				id = sc.next();

				mvo = dao.oneMonthInOut(id);
				System.out.println("사용 총액 합계: " + mvo.getBalance() + "원"); // 입금 - 출금
			}
			
			if (search == 3) {
				System.out.println("조회할 아이디를 입력해주세요: ");
				id = sc.next();
				
				if (id.equals(mvo.getId())) {
					List<MemberVO> list = dao.AccSelectAll(id);
					for (MemberVO mvo : list)
						mvo.inOutAccInfo();
				}
			}
			
			if (search == 0) {
				System.out.println("이전화면으로 돌아갑니다");
				return;
			}
		}
		
		if (aNum == 3) { // 입/출내역 정보 수정
			System.out.println("입/출내역 정보를 수정합니다");

			System.out.println("조회할 아이디를 입력해주세요: ");
			id = sc.next();

			mvo = dao.selectId(id);
			if (mvo == null) {
				System.out.println("없는 아이디 입니다");
				return;
			}
		
			List<MemberVO> list = dao.AccSelectAll(id);
			for (MemberVO mvo : list)
				mvo.inOutAccInfo();
			
					System.out.println("===== 입/출내역 수정 =====");
					System.out.println("수정 할 입/출내역의 번호(숫자만 입력)를 입력해주세요: ");
					int search = sc.nextInt();
					
					MemberVO vo = dao.selectAccNo(search);
					vo.inOutAccInfo();
					System.out.println(search + "번 내역을 수정합니다");
					
					mvo.setAccNo(search);
					
					String id2 = mvo.getId();
					
					System.out.println("수정할(입/출)구분:");
					String accInOut = sc.next();
					if (accInOut.trim().equals(""))
						accInOut = mvo.getAccInOut();

					System.out.println("수정할금액(숫자만 입력):");
					int inOutMoney = sc.nextInt();
					if (inOutMoney == 0)
						inOutMoney = mvo.getInOutMoney();

					System.out.println("수정할 날짜: ");
					String hiredate = sc.next();
					if (hiredate.trim().equals(""))
						hiredate = mvo.getHiredate();

					System.out.println("※카테고리 ex) [자기계발] [생활비] [식대] [교육] [문화] [미용] [교통] [쇼핑] [월급]");
					System.out.println("수정할 카테고리:");
					String category = sc.next();
					if (category.trim().equals(""))
						category = mvo.getCategory();
					
					System.out.println("===== 수정후 =====");
					mvo.accMember(search, id2, accInOut, inOutMoney, hiredate, category);
					
					dao.AccountUpdate(mvo);
					mvo.inOutAccInfo();
					
		} // end of if(aNum == 3)

		if (aNum == 4) { // 입/출 내역삭제
			System.out.println("입/출내역을 삭제합니다");

			System.out.println("조회할 아이디를 입력해주세요: ");
			String id2 = sc.next();

			mvo = dao.selectId(id2);
			if (mvo == null) {
				System.out.println("없는 아이디 입니다");
				return;
			}
			
			List<MemberVO> list = dao.AccSelectAll(id2);
			for (MemberVO mvo : list)
				mvo.inOutAccInfo();
			
			System.out.println("===== 입/출내역 삭제 =====");
			System.out.println("삭제할 입/출력 번호입력(숫자만 입력): ");
			int accNo = sc.nextInt();
			
			MemberVO vo = dao.selectAccNo(accNo);
			vo.inOutAccInfo();
			System.out.print("내역을 삭제 합니다");
			result = dao.AccountDelete(accNo);
			
		} // end of if(cNum == 4)

		if (aNum == 0) {
			System.out.println("이전화면으로 돌아갑니다");
			return;

		}
	}
	
	private void manageCardInOut(MemberVO member) {
		String id;
		int result;
		
		System.out.println("(1.카드사용 내역 등록, 2.카드사용 내역 조회, 3.카드사용 내역 삭제, 0.이전화면)");
		System.out.println(">>");
		int aNum = sc.nextInt();
		
		if (aNum == 1) { // 카드내역 등록
			System.out.println("카드사용내역 등록 페이지입니다");
			
			int cardNo = mvo.getCardNo();
			
			id = mvo.getId();
			
			System.out.println("날짜 입력(YY/MM/DD): ");
			String hiredate = sc.next();
			
			System.out.println("※카테고리 ex) [자기계발] [생활비] [식대] [교육] [문화] [미용] [교통] [쇼핑] [월급]");
			System.out.println("카테고리: ");
			String category = sc.next();
			
			System.out.println("사용금액 입력(숫자만 입력): ");
			int inOutMoney = sc.nextInt();
			
			System.out.println("사용한 카드의 카드번호 입력: ");
			String cardNum = sc.next();

			mvo.cardInOut(cardNo, id, hiredate, category, inOutMoney, cardNum );
			result = dao.CardInOutInsert(mvo);
			if (result == 1)
				System.out.println(id + "님 카드사용내역 등록이 완료 되었습니다");

			mvo.cardOutInfo();
		} // end of if(aNum == 1)
		
		if (aNum == 2) { // 카드내역 조회
			System.out.println("입/출내역을 조회합니다");
			System.out.println("(1.날짜별 조회, 2.카드별 총 사용 금액, 3.카드별 총 사용내역, 0.이전 화면)");
			int search = sc.nextInt();

			if (search == 1) {
				System.out.println("아이디를 입력해주세요: ");
				id = sc.next();

				System.out.println("조회할 날짜(YY/MM/DD)를 입력해 주세요: ");
				String hiredate = sc.next();

				if (id.equals(mvo.getId())) {
					List<MemberVO> list = dao.CardInOutSelect(id, hiredate);
					for (MemberVO mvo : list)
						mvo.cardOutInfo();
				}
			} else if(search == 2) {
				System.out.println("아이디를 입력해주세요: ");
				id = sc.next();
				
				System.out.println("조회할 카드번호를 입력해주세요: ");
				String cardNum = sc.next();
				
				mvo = dao.cardTotalOut(id, cardNum);
				System.out.println("입/출금 합계: " + mvo.getBalance() + "원");
			
			} else if(search == 3) {
				System.out.println("아이디를 입력해주세요: ");
				id = sc.next();
				
				System.out.println("카드번호를 입력해주세요: ");
				String cardNum = sc.next();
				
				List<MemberVO> list = dao.CardInOutSelectAll(id, cardNum);
				for (MemberVO mvo : list)
					mvo.cardOutInfo();
				
			}
		} // end of if(aNum == 2)
		
		if (aNum == 3) { // 입/출 내역삭제
			System.out.println("카드사용 내역을 삭제합니다");

			System.out.println("아이디를 입력해주세요: ");
			String id2 = sc.next();
			
			System.out.println("카드번호를 입력해주세요: ");
			String cardNum = sc.next();

			mvo = dao.selectId(id2);
			if (mvo == null) {
				System.out.println("없는 아이디 입니다");
				return;
			}
			
			List<MemberVO> list = dao.CardInOutSelectAll(id2, cardNum);
			for (MemberVO mvo : list)
				mvo.cardOutInfo();
			
			System.out.println("===== 카드사용 내역 삭제 =====");
			System.out.println("삭제할 카드사용 내역 번호입력(숫자만 입력): ");
			int cardNo = sc.nextInt();
			
			MemberVO vo = dao.CardInOutOrderSelect(id2, cardNo);
			vo.cardOutInfo();
			System.out.print("내역을 삭제 합니다");
			result = dao.CardInOutDelete(cardNo);
			
		} // end of if(cNum == 3)

		if (aNum == 0) {
			System.out.println("이전화면으로 돌아갑니다");
			return;

		}
	}

	private String inputString(String item) { //수정 서브(String)메소드
		String result = null;
		
		boolean exit = false;
		
		while(!exit) {
			System.out.print("수정할 " + item + ": ");
			result = sc.next().trim();
			switch(result) {
				case "": {
					System.out.println("잘못 입력하셨습니다.");
					break;
				}
				default : {
					exit = true;
				}
			}
		}		
		return result;
	}
	
	private int inputPrice(String item) { //수정 서브(int)메소드
		int result = 0;
		
		boolean exit = false;
		
		while(!exit) {
			System.out.print("수정할 " + item + ": ");
			result = sc.nextInt();
			switch(result) {
				case 0: {
					System.out.println("잘못 입력하셨습니다.");
					break;
				}
				default : {
					exit = true;
				}
			}
		}		
		return result;
	}
	
	public void mainMenu() { // 메인메뉴
		
		boolean exit = false;
		
		while (!exit) {
			System.out.println("작업을 선택해 주세요(1.회원가입, 2.로그인, 0.종료)");
			System.out.println(">>");
			int choice = sc.nextInt();
			
			switch (choice) {
				case 1: {
					SignUpMember();
					break;
				}
				case 2: {
					loginMember();
					break;
				}
				case 0: {
					exit = true;				
					System.out.println("프로그램을 종료 합니다");
					break;
				}
				default : {
					System.out.println("잘못 입력했습니다. 다시 입력하세요.");
				}
			}			
		} // out of while
	}
}
