/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import khangtl.db.MyConnection;
import khangtl.dtos.AnswerDTO;
import khangtl.dtos.QuestionDTO;

/**
 *
 * @author Peter
 */
public class QuestionDAO implements Serializable {

    private static Connection conn;
    private static PreparedStatement preStm;
    private static ResultSet rs;

    private void closeConnection() throws Exception {
        /*
        Close connection after connect and handle data in database
         */
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public ArrayList<QuestionDTO> searchQuestionWithCondition(String search, String subject, String status) throws Exception {
        /*
        Search question with many conditions: search text input, subject relation, status of question
         */
        ArrayList<QuestionDTO> result = null;
        ArrayList<AnswerDTO> answerList;
        String questionCont = null, answerCont;
        boolean isCorrect;
        Date createDate = null;
        HashMap<String, Object> params = new HashMap<>();
        params.put("q.Cont", search);
        params.put("q.Subject", subject);
        params.put("q.Status", status);
        String sql = "SELECT q.Id, q.Cont as QuestionCont, q.Status, q.Subject, q.CreateDate, a.Cont as AnswerCont, "
                + "a.Correctly FROM Questions q JOIN Answers a ON a.QuestionId = q.Id";
        boolean isFirst = true;
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (!value.toString().trim().isEmpty()) {
                if (isFirst) {
                    if (key.equals("q.Cont")) {
                        sql += " WHERE " + key + "LIKE ?";
                    } else {
                        sql += " WHERE " + key + "= ?";
                    }
                    isFirst = false;
                } else {
                    sql += " AND " + key + "= ?";
                }
            }
        }
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int paramsNum = 1;
            for (String key : params.keySet()) {
                Object value = params.get(key);
                if (!value.equals("")) {
                    if (value instanceof String) {
                        preStm.setString(paramsNum, value.toString());
                    }
                    paramsNum++;
                }
            }
            rs = preStm.executeQuery();
            int tmpId = 0;
            answerList = new ArrayList<>();
            result = new ArrayList<>();
            while (rs.next()) {
                if (tmpId == 0) {
                    tmpId = rs.getInt("Id");
                }
                if (tmpId != rs.getInt("Id") && tmpId != 0) {
                    result.add(new QuestionDTO(tmpId, questionCont, status, subject, createDate, answerList));
                    answerList = new ArrayList<>();
                } else if (rs.isLast()) {
                    result.add(new QuestionDTO(tmpId, questionCont, status, subject, createDate, answerList));
                }

                tmpId = rs.getInt("Id");
                subject = rs.getString("Subject");
                questionCont = rs.getString("QuestionCont");
                createDate = rs.getDate("CreateDate");

                answerCont = rs.getString("AnswerCont");
                isCorrect = rs.getBoolean("Correctly");
                answerList.add(new AnswerDTO(answerCont, isCorrect));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public QuestionDTO getQuestionById(int id) throws Exception {
        QuestionDTO dto = null;
        ArrayList<AnswerDTO> answerList;
        String cont, subject, answerCont;
        boolean isCorrect;
        String sql = "SELECT q.Id, q.Cont as QuestionCont, q.Subject, q.CreateDate, a.Cont as AnswerCont, "
                + "a.Correctly FROM Questions q JOIN Answers a ON a.QuestionId = q.Id WHERE q.Id = ?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = preStm.executeQuery();
            answerList = new ArrayList<>();
            while (rs.next()) {
                answerCont = rs.getString("AnswerCont");
                isCorrect = rs.getBoolean("Correctly");
                answerList.add(new AnswerDTO(answerCont, isCorrect));
                if (rs.isLast()) {
                    cont = rs.getString("QuestionCont");
                    subject = rs.getString("Subject");
                    dto = new QuestionDTO(id, cont, subject, answerList);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    public boolean checkExistQuestion(String cont, String subject) throws Exception {
        boolean isExisted = false;
        String sql = "SELECT Cont, Subject FROM Questions WHERE Cont LIKE ? AND Subject=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, "%" + cont + "%");
            preStm.setString(2, subject);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isExisted = true;
            }
        } finally {
            closeConnection();
        }
        return isExisted;
    }

    public boolean insertQuestion(QuestionDTO dto) throws Exception {
        /*
        Insert list of question into database
         */
        boolean isInserted = false;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        time = sf.parse(sf.format(time));
        String insertQuestionSQL = "INSERT INTO Questions(Cont, Status, Subject, CreateDate) VALUES(?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(insertQuestionSQL);
            preStm.setString(1, dto.getCont());
            preStm.setString(2, dto.getStatus());
            preStm.setString(3, dto.getSubject());
            preStm.setTimestamp(4, new Timestamp(time.getTime()));
            isInserted = preStm.executeUpdate() > 0;
            if (isInserted) {
                String selectSQL = "SELECT TOP 1 Id FROM Questions WHERE Cont LIKE ? AND CreateDate=? ORDER BY CreateDate DESC";
                preStm = conn.prepareStatement(selectSQL);
                preStm.setString(1, dto.getCont());
                preStm.setTimestamp(2, new Timestamp(time.getTime()));
                rs = preStm.executeQuery();
                if (rs.next()) {
                    String insertAnswerSQL = "INSERT INTO Answers(Cont, QuestionId, Correctly) VALUES(?,?,?)";
                    int id = rs.getInt("Id");
                    // Just update database after conn.commit() called
                    conn.setAutoCommit(false);
                    preStm = conn.prepareStatement(insertAnswerSQL);
                    for (AnswerDTO ans : dto.getAnswerList()) {
                        preStm.setString(1, ans.getCont());
                        preStm.setInt(2, id);
                        preStm.setBoolean(3, ans.isIsCorrect());
                        isInserted = preStm.executeUpdate() > 0;
                    }
                    conn.commit();
                }
            }
        } catch (SQLException se) {
            conn.rollback();
        } finally {
            closeConnection();
        }
        return isInserted;
    }

    public boolean updateQuestion(int id, QuestionDTO dto) throws Exception {
        /*
        Update question existed in database
         */
        boolean isUpdated = false;
        String updateQuestionSQL = "UPDATE Questions SET Cont=?, Subject=? WHERE Id=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(updateQuestionSQL);
            preStm.setString(1, dto.getCont());
            preStm.setString(2, dto.getSubject());
            preStm.setInt(3, id);
            isUpdated = preStm.executeUpdate() > 0;
            if (isUpdated) {
                String insertAnswerSQL = "UPDATE Answers SET Cont=?, Correctly=? WHERE QuestionId=?";
                // Just update database after conn.commit() called
                conn.setAutoCommit(false);
                preStm = conn.prepareStatement(insertAnswerSQL);
                for (AnswerDTO ans : dto.getAnswerList()) {
                    preStm.setString(1, ans.getCont());
                    preStm.setBoolean(2, ans.isIsCorrect());
                    preStm.setInt(3, id);
                    preStm.executeUpdate();
                }
                conn.commit();
            }
        } finally {
            closeConnection();
        }
        return isUpdated;
    }

    public boolean updateQuestionById(int id) throws Exception {
        boolean isUpdated = false;
        String sql = "UPDATE Questions SET Cont = ? WHERE Id = ?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            isUpdated = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isUpdated;
    }

    public boolean deleteQuestionById(int id) throws Exception {
        boolean isDeleted = false;
        String sql = "UPDATE Questions SET Status = 'Deactive' WHERE Id = ?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            isDeleted = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return isDeleted;
    }

    public ArrayList<QuestionDTO> getListQuestionToTest(String subject) throws Exception {
        ArrayList<QuestionDTO> result = null;
        ArrayList<AnswerDTO> answerList;
        String questionCont = null;
        String answerCont;
        int answerId;
        String sql = "DECLARE @count int SET @count = (SELECT s.QuestionQuantity FROM Subjects s "
                + "WHERE s.Id=?) SELECT qq.Id as QuestionId, qq.Cont as QuestionCont, aa.Id "
                + "as AnswerId, aa.Cont as AnswerCont FROM (SELECT TOP (@count) q.Id, q.Cont FROM "
                + "Questions q JOIN Subjects s ON q.Subject = s.Id WHERE Subject=? AND "
                + "Status='Active') qq JOIN Answers aa ON aa.QuestionId = qq.Id";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preStm.setString(1, subject);
            preStm.setString(2, subject);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            answerList = new ArrayList<>();
            int tmpId = 0;
            while (rs.next()) {
                if (tmpId == 0) {
                    tmpId = rs.getInt("QuestionId");
                }
                if (tmpId != rs.getInt("QuestionId") && tmpId != 0) {
                    result.add(new QuestionDTO(tmpId, questionCont, answerList));
                    answerList = new ArrayList<>();
                } else if (rs.isLast()) {
                    result.add(new QuestionDTO(tmpId, questionCont, answerList));
                }

                tmpId = rs.getInt("QuestionId");
                questionCont = rs.getString("QuestionCont");
                answerId = rs.getInt("AnswerId");
                answerCont = rs.getString("AnswerCont");
                answerList.add(new AnswerDTO(answerId, answerCont));
            }

        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTimeToTest(String subject) throws Exception {
        int testTime = 0;
        String sql = "SELECT TestTime FROM Subjects WHERE Id=?";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, subject);
            rs = preStm.executeQuery();
            if (rs.next()) {
                testTime = rs.getInt("TestTime");
            }
        } finally {
            closeConnection();
        }
        return testTime;
    }

    public int getNumOfTrue(HashMap<Integer, Integer> submitList) throws Exception {
        int point = 0;
        String insertHistorySQL = "INSERT INTO Histories(QuestionId, AnswerId) VALUES(?,?)";
        try {
            conn = MyConnection.getConnection();
            conn.setAutoCommit(false);
            preStm = conn.prepareStatement(insertHistorySQL);
            for (Map.Entry pair : submitList.entrySet()) {
                preStm.setInt(1, (int) pair.getKey());
                preStm.setInt(2, (int) pair.getValue());
                preStm.executeUpdate();
            }
            conn.commit();
            String selectPointSQL = "DECLARE @count int SET @count = ? SELECT COUNT(history.Correctly) as numOfTrue "
                    + "FROM (SELECT TOP (@count) h.QuestionId, h.AnswerId, a.Correctly FROM Histories h JOIN Answers a "
                    + "ON h.AnswerId = a.Id ORDER BY h.Id DESC) history WHERE history.Correctly = 1";
            preStm = conn.prepareStatement(selectPointSQL);
            preStm.setInt(1, submitList.size());
            rs = preStm.executeQuery();
            if (rs.next()) {
                point = rs.getInt("numOfTrue");
            }
        } catch (SQLException se) {
            conn.rollback();
        } finally {
            closeConnection();
        }
        return point;
    }

    public boolean finalSubmit(String email, double point, String subject, java.util.Date startTime, HashMap<Integer, Integer> submitList) throws Exception {
        boolean checkSubmit = false;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date submitTime = new java.util.Date();
        submitTime = sf.parse(sf.format(submitTime));
        String insertSubmitSQL = "INSERT INTO Submits(UserEmail, Point, SubjectId, StartTime, EndTime) VALUES(?,?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            preStm = conn.prepareStatement(insertSubmitSQL);
            preStm.setString(1, email);
            preStm.setFloat(2, (float) point);
            preStm.setString(3, subject);
            preStm.setTimestamp(4, new Timestamp(startTime.getTime()));
            preStm.setTimestamp(5, new Timestamp(submitTime.getTime()));
            checkSubmit = preStm.executeUpdate() > 0;
//            if (checkSubmit) {
//                String selectSQL = "SELECT TOP 1 Id FROM Submits WHERE UserEmail = ? AND SubjectId = ? AND SubmitTime = ?";
//                preStm = conn.prepareStatement(selectSQL);
//                preStm.setString(1, email);
//                preStm.setString(2, subject);
//                preStm.setTimestamp(3, new Timestamp(submitTime.getTime()));
//                rs = preStm.executeQuery();
//                if (rs.next()) {
//                    int id = rs.getInt("Id");
//                    String updateHistorySQL = "DECLARE @count int SET @count = ? UPDATE TOP (@count) newHistory SET SubmitId = ? FROM "
//                            + "(SELECT TOP (@count) h.QuestionId, h.AnswerId, h.SubmitId FROM Histories h ORDER BY h.Id DESC) newHistory";
//                    preStm = conn.prepareStatement(updateHistorySQL);
//                    preStm.setInt(1, submitList.size());
//                    preStm.setInt(2, id);
//                    checkSubmit = preStm.executeUpdate() > 0;
//                }
//            }
        } catch (SQLException se) {
            conn.rollback();
        } finally {
            closeConnection();
        }
        return checkSubmit;
    }
}
