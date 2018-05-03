import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.sql.*;
import java.util.*;

/**
 * Created by fengtao on 2018/5/2.
 */
public class Presentative {
    public static void main(String[] args){

        Presentative dao = new Presentative();
        List<String> presentativeNullList=dao.getSelectNullList();
        for(String id:presentativeNullList){
            JSONArray ja = dao.getSelect(id);
            System.out.println(ja);
            try {
                dao.update(ja.toString(),id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List<String> getSelectNullList() {

        // sql语句
        String sql = "SELECT id from kx_kq_store WHERE presentative is NULL OR presentative=''";
        // 获取到连接
        Connection conn = getCon();
        PreparedStatement pst = null;
        List<String> nullList=new ArrayList();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // 将查询出的内容添加到list中，其中id为数据库中的字段名称
                nullList.add(rs.getString("id"));
            }
            closeConnection(rs, pst, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nullList;
    }
    public void update(String ja,String id) throws Exception{
            String sql = "update kx_kq_store set presentative=? WHERE id="+id;
            Connection conn = getCon();
            PreparedStatement pst = null;
            pst = (PreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, ja);
            pst.executeUpdate();

            closeConnection(null,pst,conn);
    }

    public JSONArray getSelect(String id) {
        // sql语句
        String sql = "SELECT\n" +
                "  pu.sex as userinfoid__sex,pu.userinfoid,po.codepath,po3.orgstructid as parentorgstructid__parentorgstructid,po.parentorgstructid,\n" +
                "  po3.orgname as parentorgstructid__parentorgstructid__orgname,po.orgname||'('||po3.orgname||','||po2.orgname||')' as orgname,\n" +
                "  pu.phonenumber as userinfoid__phonenumber,po.orgtypeid as orgtypeid,po.orgname||'('||po3.orgname||','||po2.orgname||')' as text,\n" +
                "  ksr.isdefault,po.orgstructid,po2.orgname as parentorgstructid__orgname,po.seq,po.orgstructid as key,posal.orgname as salearaname,\n" +
                "  pu.jobnumber as userinfoid__jobnumber,po2.orgname as positionname,po.orgstructtypeid,pu.email as userinfoid__email,po.fullname,pu.accountcode,\n" +
                "  pu.userinfoname as membername,pu.status\n" +
                "FROM kx_kq_storerepresentative ksr\n" +
                "LEFT JOIN kx_kq_store ks on ksr.storeid=ks.id\n" +
                "LEFT JOIN pl_orgstruct po on ksr.representativeid=po.orgstructid\n" +
                "LEFT JOIN pl_orgstruct po2 on po.parentorgstructid=po2.orgstructid\n" +
                "LEFT JOIN pl_orgstruct po3 on po2.parentorgstructid=po3.orgstructid\n" +
                "LEFT JOIN pl_orgstruct posal on ks.seleareaid=posal.orgstructid\n" +
                "LEFT JOIN pl_userinfo pu on po.userinfoid=pu.userinfoid\n" +
                "WHERE\n" +
                "\tks.id ="+id;
        // 获取到连接
        Connection conn = getCon();
        PreparedStatement pst = null;
        // 定义一个json用于接受数据库查询到的内容
        JSONArray json = new JSONArray();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Map<String,String> jo = new HashMap();
                jo.put("userinfoid__sex", rs.getString("userinfoid__sex"));
                jo.put("userinfoid", rs.getString("userinfoid"));
                jo.put("codepath", rs.getString("codepath"));
                jo.put("parentorgstructid__parentorgstructid", rs.getString("parentorgstructid__parentorgstructid"));
                jo.put("parentorgstructid", rs.getString("parentorgstructid"));
                jo.put("parentorgstructid__parentorgstructid__orgname", rs.getString("parentorgstructid__parentorgstructid__orgname"));
                jo.put("orgname", rs.getString("orgname"));
                jo.put("userinfoid__phonenumber", rs.getString("userinfoid__phonenumber"));
                jo.put("orgtypeid", rs.getString("orgtypeid"));
                jo.put("text", rs.getString("text"));
                jo.put("isdefault", rs.getString("isdefault"));
                jo.put("orgstructid", rs.getString("orgstructid"));
                jo.put("parentorgstructid__orgname", rs.getString("parentorgstructid__orgname"));
                jo.put("seq", rs.getString("seq"));
                jo.put("key", rs.getString("key"));
                jo.put("salearaname", rs.getString("salearaname"));
                jo.put("userinfoid__jobnumber", rs.getString("userinfoid__jobnumber"));
                jo.put("positionname", rs.getString("positionname"));
                jo.put("orgstructtypeid", rs.getString("orgstructtypeid"));
                jo.put("userinfoid__email", rs.getString("userinfoid__email"));
                jo.put("fullname", rs.getString("fullname"));
                jo.put("accountcode", rs.getString("accountcode"));
                jo.put("membername", rs.getString("membername"));
                jo.put("status", rs.getString("status"));

                for(String s:jo.keySet()){
                    System.out.println("key : "+s+" value : "+jo.get(s));
                    if(null==jo.get(s)){
                        jo.put(s,"");
                    }
                }

                String mapString = JSON.toJSONString(jo, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jo2 = JSON.parseObject(mapString);

                json.add(jo2);
            }
            closeConnection(rs, pst, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public Connection getCon() {
        //数据库连接名称
        String username="postgres";
        //数据库连接密码
        String password="123456";
        String driver="org.postgresql.Driver";
        //其中tenant_1008201为数据库名称
        String url="jdbc:postgresql://***/***";
        Connection conn=null;
        try{
            Class.forName(driver);
            conn=(Connection) DriverManager.getConnection(url,username,password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭数据库连接
    public static void closeConnection(ResultSet rst, PreparedStatement pst,
                                       Connection conn) throws Exception {
        try {

            if (rst != null) {
                rst.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
