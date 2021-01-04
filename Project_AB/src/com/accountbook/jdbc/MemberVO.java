package com.accountbook.jdbc;

public class MemberVO {
	// 회원가입
	private String id;
	private String password;
	private String name;
	private String jumin;
	private String phone;
	private String email;
	
	//입/출력 등록
	private int accNo; // 입/출 시퀀스
	private String accInOut; // 입금/출금
	private int balance; // 계좌 +-구분
	
	//카드 등록
	private String card;	// 카드사
	private String cardNum; // 카드번호
	private String cardNick; // 카드별칭
	private String cardModiNick; // 변경할 카드 명칭
	private int cardNo; // 카드 시퀀스
	
	//공통
	private int inOutMoney; // 입출금액
	private String hiredate; // 날짜
	private String category; // 사용 유형
	
	public MemberVO() {
	}
	
	//회원가입
	public MemberVO(String id, String password, String name, String jumin, String phone, String email) {
		super();
		this.id = id;           
		this.password = password;
		this.name = name;
		this.jumin = jumin;
		this.phone = phone;
		this.email = email;
	}
	
	//입/출 등록
	public void accMember(int accNo, String id, String accInOut, int inOutMoney, String hiredate, String category) {
		this.accNo = accNo;
		this.id = id;
		this.accInOut = accInOut;
		this.inOutMoney = inOutMoney;
		this.hiredate = hiredate;
		this.category = category;
	}
	
	//카드 등록
	public void CardMember(String id, String card, String cardNum, String cardNick) {
		this.id = id;
		this.card = card;
		this.cardNum = cardNum;
		this.cardNick = cardNick;
	}
	
	//카드 사용내역 출력
	public void cardInOut(int cardNo, String id, String hiredate, String category, int inOutMoney, String cardNum) {
		this.cardNo = cardNo;
		this.id = id;
		this.hiredate = hiredate;
		this.category = category;
		this.inOutMoney = inOutMoney;
		this.cardNum = cardNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getAccNo() {
		return accNo;
	}

	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public String getAccInOut() {
		return accInOut;
	}

	public void setAccInOut(String accInOut) {
		this.accInOut = accInOut;
	}

	public int getCardNo() {
		return cardNo;
	}

	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	
	public String getCardNick() {
		return cardNick;
	}

	public void setCardNick(String cardNick) {
		this.cardNick = cardNick;
	}
	
	public String getCardModiNick() {
		return cardModiNick;
	}

	public void setCardModiNick(String cardModiNick) {
		this.cardModiNick = cardModiNick;
	}

	public int getInOutMoney() {
		return inOutMoney;
	}

	public void setInOutMoney(int inOutMoney) {
		this.inOutMoney = inOutMoney;
	}

	public String getHiredate() {
		return hiredate;
	}

	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	//회원 정보 출력
	@Override
	public String toString() { // 멤버 출력
		return "MemberVO [아이디:" + id + ", 비밀번호:" + password + ", 이름:" + name + ", 주민번호:" + jumin
				+ ", 핸드폰:" + phone + ", 이메일:" + email + "]";
	}
	
	// 입/출 정보 출력
	public void inOutAccInfo() {
		System.out.println("번호: " + accNo + ", 아이디:" + id + ", 입/출:" + accInOut + ", 입/출금액:" + inOutMoney + "원" 
				+ ", 날짜:" + hiredate + ", 카테고리:" + category);
	}

	//카드 정보 출력
	public void cardInfo() {
		System.out.println("아이디:" + id + ", 카드사: " + card + ", 카드 번호: "  + cardNum + ", 카드별칭:" + cardNick);
	}
	
	//카드 사용 내역 출력
	public void cardOutInfo() {
		System.out.println("번호: " + cardNo + ", 아이디:" + id + ", 날짜" + hiredate + ", 카테고리:" + category 
					+ ", 금액:" + inOutMoney + "원" + ", 카드번호:" + cardNum);	
	}
	
}
