package kr.tpmc.config;

public class Config {
    @Instance
    public static Config config;

    @Comment(comments = {"MySQL 사용 여부", "true: MySQL 사용", "false: SQLite 사용"})
    public boolean MySQL = false;

    @Comment(comments = {"MySQL 사용시 작성", "{db 이름}을 작성해 주세요"})
    public String MySQL_url = "jdbc:mysql://localhost:3306/{db 이름}";

    @Comment(comments = {"MySQL 사용시 작성", "{사용자}를 작성해 주세요"})
    public String MySQL_user = "{사용자}";

    @Comment(comments = {"MySQL 사용시 작성", "{비밀 번호}를 작성해 주세요"})
    public String MySQL_password = "{비밀 번호}";

    @Comment(comments = {"economy 에서 쓸 단위명"})
    public String economy_name = "원";
}
