package next.dao;

import core.jdbc.*;
import next.model.Question;
import next.model.User;

import java.sql.*;
import java.util.List;

public class QuestionDao {
	public void increaseCountOfAnswer(Long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "UPDATE QUESTIONS SET countOfAnswer = countOfAnswer + 1 WHERE questionId = ?";

		PreparedStatementSetter pss = new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setLong(1, questionId);
			}
		};

		jdbcTemplate.update(sql, pss);
	}

	public Question insert(Question question) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "INSERT INTO QUESTIONS " +
				"(writer, title, contents, createdDate) " +
				" VALUES (?, ?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, question.getWriter());
				pstmt.setString(2, question.getTitle());
				pstmt.setString(3, question.getContents());
				pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
				return pstmt;
			}
		};

		KeyHolder keyHolder = new KeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		return findById(keyHolder.getId());
	}

	public Question update(Long targetQuestionId, Question updateData) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

		String sql = "UPDATE QUESTIONS SET title = ?, contents = ? WHERE questionId = ?";

		jdbcTemplate.update(sql, ((preparedStatement) -> {
			preparedStatement.setString(1, updateData.getTitle());
			preparedStatement.setString(2, updateData.getContents());
			preparedStatement.setLong(3, targetQuestionId);
		}));
		return findById(targetQuestionId);
	}

	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
				+ "order by questionId desc";

		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
			}

		};

		return jdbcTemplate.query(sql, rm);
	}

	public Question findById(long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
				+ "WHERE questionId = ?";

		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
			}
		};

		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}

	public User findWriterById(long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "SELECT userId, name, email FROM USERS WHERE userid = (SELECT writer FROM QUESTIONS WHERE questionId = ?)";

		return jdbcTemplate.queryForObject(sql, new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs) throws SQLException {
				User result = new User(rs.getString("userId"), rs.getString("name"), rs.getString("email"));
				return result;
			}
		}, new PreparedStatementSetter() {
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setLong(1, questionId);
			}
		});
	}

	public void deleteQuestion(long questionId) {
		JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();
		String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";

		jdbcTemplate.update(sql, (preparedStatement) -> {
			preparedStatement.setLong(1, questionId);
		});
	}
}
