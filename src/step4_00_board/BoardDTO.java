package step4_00_board;

public class BoardDTO {
	
	private int num;
	private String writer;
	private String email;
	private String title;
	private String password;
	private String reg_date;
	private int ref;
	private int re_step;
	private int re_level;
	private int read_count;
	private String content;
	
	public BoardDTO() {}
	public BoardDTO(int num, String writer, String email, String title, String password, String reg_date, int ref,
			int re_step, int re_level, String content) {
		super();
		this.num = num;
		this.writer = writer;
		this.email = email;
		this.title = title;
		this.password = password;
		this.reg_date = reg_date;
		this.ref = ref;
		this.re_step = re_step;
		this.re_level = re_level;
		this.content = content;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getRe_step() {
		return re_step;
	}
	public void setRe_step(int re_step) {
		this.re_step = re_step;
	}
	public int getRe_level() {
		return re_level;
	}
	public void setRe_level(int re_level) {
		this.re_level = re_level;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	

}
