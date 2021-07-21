package com.javaex.vo;

public class BoardVo {

	// 필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String reg_date;
	private int user_no;
	private String id;
	private String pw;
	private String name;
	private String gender;
	private int rownum;

	// 생성자
	public BoardVo() {

	}

	public BoardVo(int no, String id, String name, String title, int hit, String content, String reg_date,
			int user_no) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.id = id;
		this.user_no = user_no;
		this.name = name;
	}

	public BoardVo(String title, String content, int user_no) {
		this.user_no = user_no;
		this.title = title;
		this.content = content;
	}

	public BoardVo(int no, String title, String content) {
		this.no = no;
		this.title = title;
		this.content = content;
	}

	public BoardVo(int rounum, int no, String title, int hit, String name, String content, String reg_date,
			int user_no) {
		this.rownum = rounum;
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.reg_date = reg_date;
		this.content = content;
		this.name = name;
		this.user_no = user_no;
	}

	public BoardVo(int no, String name, int hit, String reg_date, String title, String content, int user_no) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.name = name;
		this.user_no = user_no;
	}

	public BoardVo(int no, String title, String content, int hit, String reg_date, int user_no) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.user_no = user_no;
	}

	public BoardVo(int no, String id, String pw, String name, String gender, String title, String content, int hit,
			String reg_date, int user_no) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.user_no = user_no;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.gender = gender;
	}

	// 메소드 - GS

	public int getNo() {
		return no;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	// 메소드 - 일반
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", reg_date="
				+ reg_date + ", user_no=" + user_no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", gender="
				+ gender + "]";
	}

}