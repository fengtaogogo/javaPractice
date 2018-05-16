package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.domain.IDUtil;
import com.example.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class HandlerService implements Runnable{
    private IDUtil idWorker;
    private PersonMapper personMapper;
    private int num;//线程次数
    private int PersonSize;//每个线程处理业代数
    private List<Map<String,Object>> Yewuyuans;
    private List<Map<String,Object>> storetype;
    private List<Map<String,Object>> storelevel;
    private int [] regionid;
    private List<Map<String,Object>> brands;
    private List<Map<String,Object>> categories;

    public HandlerService() {
    }

    public HandlerService(Integer num, int PersonSize, List<Map<String,Object>> Yewuyuans, List<Map<String,Object>> storetype,
                          List<Map<String,Object>> storelevel, int [] regionid, PersonMapper personMapper,IDUtil idWorker,
                          List<Map<String,Object>> brands,List<Map<String,Object>> categories) {
        this.num = num;
        this.PersonSize=PersonSize;
        this.Yewuyuans=Yewuyuans;
        this.storetype=storetype;
        this.storelevel=storelevel;
        this.regionid=regionid;
        this.personMapper=personMapper;
        this.idWorker=idWorker;
        this.brands=brands;
        this.categories=categories;
    }
    @Override
    public void run() {

        long starttime=System.currentTimeMillis();
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("开始时间记录："+df2.format(new Date()));
        int m=num*PersonSize;
        int n=(num+1)*PersonSize;
        for (int j = m; j<n; j++) {

            System.out.println("业代名称："+Yewuyuans.get(j).get("userinfoname")+",所在组织："+Yewuyuans.get(j).get("orgname"));
            //随机取终端类型
            BigInteger stType =new BigInteger(storetype.get(new Random().nextInt(storetype.size())).get("dickey")+"");
            //随机取品牌
            Map brandsMap=brands.get(new Random().nextInt(brands.size()));
            String bran =brandsMap.get("brandjson").toString();
            BigInteger brandid=new BigInteger(brandsMap.get("dickey").toString());
            //随机取品类1
            BigInteger category1 =new BigInteger(categories.get(new Random().nextInt(categories.size())).get("dickey")+"");
            //随机取品类2
            BigInteger category2 =new BigInteger(categories.get(new Random().nextInt(categories.size())).get("dickey")+"");
            //        渠道类型根据终端类型反求，注意
            List<Map<String,Object>> channeltype=personMapper.selectchanneltype(stType);
            BigInteger chanType =new BigInteger(channeltype.get(new Random().nextInt(channeltype.size())).get("dickey")+"");
            BigInteger stLevel =new BigInteger(storelevel.get(new Random().nextInt(storelevel.size())).get("dickey")+"");
            int regId = regionid[(int)(Math.random()*(regionid.length))];
            BigInteger custoType =new BigInteger("905324680615956480");

            //根据营销组织查渠道：取的是业务员营销组织上一级的渠道供货商
            BigInteger orgid=new BigInteger(Yewuyuans.get(j).get("saleareaid").toString());
            List<Map<String,Object>> channels = personMapper.selectchannels(orgid);
//随机取两个不同数用于随机生成渠道供货商
            int[] reult = randomCommon(0,channels.size(),2);
//循环插入终端及其关联信息
            for (int i = 0; i < 400; i++) {
//              广东省经度：109.75-117.333333、纬度：20.15-25.516667
                DecimalFormat df = new DecimalFormat( "0.000000 ");
                //随机生成经纬度
                double lon =Double.parseDouble(df.format(Double.parseDouble(randomLonLat(109.750000,117.333333))));
                double lat =Double.parseDouble(df.format(Double.parseDouble(randomLonLat(20.150000,25.516667))));
                String address="{\"address\":\"广东省\",\"latitude\":\""+lat+"\",\"longitude\":\""+lon+"\"}";
//分布式生成18位不重复数字ID-终端id
                Long storeId=idWorker.nextId();
                //品类json拼装生成
                JSONArray jsonCategory = new JSONArray();
                HashMap CategoryrMap = new HashMap();
                CategoryrMap.put("isselected",0+"");
                CategoryrMap.put("key",category1+"");
                CategoryrMap.put("id",category1+"");
                CategoryrMap.put("status",1+"");
                String mapStringCate = JSON.toJSONString(CategoryrMap, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
                JSONObject joCate = JSON.parseObject(mapStringCate);
                jsonCategory.add(joCate);

                HashMap storeCategoryMap21 = new HashMap();
                storeCategoryMap21.put("storeid",storeId);
                storeCategoryMap21.put("categoryid",category1);
                storeCategoryMap21.put("id",idWorker.nextId());
                storeCategoryMap21.put("isdefault",0);
                personMapper.insertcategory(storeCategoryMap21);


                if(category1!=category2){
                    HashMap CategoryrMap2 = new HashMap();
                    CategoryrMap2.put("isselected",0+"");
                    CategoryrMap2.put("key",category2+"");
                    CategoryrMap2.put("id",category2+"");
                    CategoryrMap2.put("status",1+"");
                    String mapStringCate2 = JSON.toJSONString(CategoryrMap2, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
                    JSONObject joCate2 = JSON.parseObject(mapStringCate2);
                    jsonCategory.add(joCate2);

                    HashMap storeCategoryMap22 = new HashMap();
                    storeCategoryMap22.put("storeid",storeId);
                    storeCategoryMap22.put("categoryid",category2);
                    storeCategoryMap22.put("id",idWorker.nextId());
                    storeCategoryMap22.put("isdefault",0);
                    personMapper.insertcategory(storeCategoryMap22);
                }

//渠道终端关联表
                HashMap storesupplierMap21 = new HashMap();
                storesupplierMap21.put("storeid",storeId);
                storesupplierMap21.put("supplierid",channels.get(reult[0]).get("id"));
                storesupplierMap21.put("id",idWorker.nextId());
                storesupplierMap21.put("isdefault",0);
                personMapper.insertStoresupplier(storesupplierMap21);

                HashMap storesupplierMap22 = new HashMap();
                storesupplierMap22.put("storeid",storeId);
                storesupplierMap22.put("supplierid",channels.get(reult[1]).get("id"));
                storesupplierMap22.put("id",idWorker.nextId());
                storesupplierMap22.put("isdefault",0);
                personMapper.insertStoresupplier(storesupplierMap22);
//供货商字段拼装
                JSONArray json = new JSONArray();
                Map<String,String> jo = new HashMap();
                jo.put("id", channels.get(reult[0]).get("id").toString());
                jo.put("text", channels.get(reult[0]).get("channelname").toString());
                jo.put("key", channels.get(reult[0]).get("id").toString());
                jo.put("label", channels.get(reult[0]).get("channelname").toString());
                jo.put("isdefault", "1");
                String mapString = JSON.toJSONString(jo, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jo2 = JSON.parseObject(mapString);
                json.add(jo2);
                Map<String,String> jotwo = new HashMap();
                jotwo.put("id", channels.get(reult[1]).get("id").toString());
                jotwo.put("text", channels.get(reult[1]).get("channelname").toString());
                jotwo.put("key", channels.get(reult[1]).get("id").toString());
                jotwo.put("label", channels.get(reult[1]).get("channelname").toString());
                jotwo.put("isdefault", "0");
                String mapStringtwo = JSON.toJSONString(jotwo, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jo2two = JSON.parseObject(mapStringtwo);
                json.add(jo2two);

//                String custoCode = UUID.randomUUID().toString().replaceAll("-", "");
                String custoCode ="ZD"+j+"000"+i;//终端编码
//插入终端表
                HashMap paramMap = new HashMap();
                paramMap.put("id",storeId);
                paramMap.put("storecode",custoCode);
                paramMap.put("saleareaid", Yewuyuans.get(j).get("saleareaid"));
                paramMap.put("storetype", stType);
                paramMap.put("channeltype", chanType);
                paramMap.put("storelevel", stLevel);
                paramMap.put("regionid", regId);
                paramMap.put("contactphone", new BigInteger(getTel()));
                paramMap.put("contactname", "联系人forTest"+j+"000"+i);
                paramMap.put("address", address);
                paramMap.put("storename", "终端forTest"+j+"000"+i);
                paramMap.put("customertype",custoType);
                paramMap.put("status",1);
                paramMap.put("createop", Yewuyuans.get(j).get("orgstructid"));
                paramMap.put("createtime", getStringDate());
                paramMap.put("updateop", Yewuyuans.get(j).get("orgstructid"));
                paramMap.put("updatetime", getStringDate());
                paramMap.put("longitude", lon);
                paramMap.put("latitude", lat);
                paramMap.put("supplier", json.toString());
                paramMap.put("brand",bran);
                paramMap.put("category",jsonCategory.toString());
                personMapper.insertStores(paramMap);
//插入客户表
                HashMap paramMapCustomer = new HashMap();
                paramMapCustomer.put("id",storeId);
                paramMapCustomer.put("customercode",custoCode);
                paramMapCustomer.put("customername","终端forTest"+j+"000"+i);
                paramMapCustomer.put("saleareaid", Yewuyuans.get(j).get("saleareaid"));
                paramMapCustomer.put("status",1);
                paramMapCustomer.put("customertype",custoType);
                paramMapCustomer.put("createop", Yewuyuans.get(j).get("orgstructid"));
                paramMapCustomer.put("createtime", getStringDate());
                paramMapCustomer.put("updateop", Yewuyuans.get(j).get("orgstructid"));
                paramMapCustomer.put("updatetime", getStringDate());
                personMapper.insertCustomers(paramMapCustomer);
//业代终端关联表
                HashMap paramMapRepresentative = new HashMap();
                paramMapRepresentative.put("id",idWorker.nextId());
                paramMapRepresentative.put("storeid",storeId);
                paramMapRepresentative.put("representativeid",Yewuyuans.get(j).get("orgstructid"));
                paramMapRepresentative.put("isdefault",1);
                personMapper.insertRepresentatives(paramMapRepresentative);
//品牌
                HashMap paramMapbrand = new HashMap();
                paramMapbrand.put("id",idWorker.nextId());
                paramMapbrand.put("storeid",storeId);
                paramMapbrand.put("brandid",brandid);
                paramMapbrand.put("isdefault",0);
                personMapper.insertStorebrand(paramMapbrand);

                //生成终端的负责人字段
                HashMap paramMapstoreId = new HashMap();
                paramMapstoreId.put("id",storeId);
                //查询得到终端和其业代的信息
                List<Map<String,Object>> presentatives=personMapper.selectPresentatives(paramMapstoreId);

                Map<String,String> jopresentatives = new HashMap();
                jopresentatives.put("userinfoid", getMapValue(presentatives.get(0),"userinfoid"));
                jopresentatives.put("codepath", getMapValue(presentatives.get(0),"codepath"));
                jopresentatives.put("parentorgstructid__parentorgstructid", getMapValue(presentatives.get(0),"parentorgstructid__parentorgstructid"));
                jopresentatives.put("parentorgstructid", getMapValue(presentatives.get(0),"parentorgstructid"));
                jopresentatives.put("parentorgstructid__parentorgstructid__orgname", getMapValue(presentatives.get(0),"parentorgstructid__parentorgstructid__orgname"));
                jopresentatives.put("orgname", getMapValue(presentatives.get(0),"orgname"));
                jopresentatives.put("userinfoid__phonenumber", getMapValue(presentatives.get(0),"userinfoid__phonenumber"));
                jopresentatives.put("orgtypeid", getMapValue(presentatives.get(0),"orgtypeid"));
                jopresentatives.put("text", getMapValue(presentatives.get(0),"text"));
                jopresentatives.put("isdefault", getMapValue(presentatives.get(0),"isdefault"));
                jopresentatives.put("orgstructid", getMapValue(presentatives.get(0),"orgstructid"));
                jopresentatives.put("parentorgstructid__orgname", getMapValue(presentatives.get(0),"parentorgstructid__orgname"));
                jopresentatives.put("seq", getMapValue(presentatives.get(0),"seq"));
                jopresentatives.put("key", getMapValue(presentatives.get(0),"key"));
                jopresentatives.put("salearaname", getMapValue(presentatives.get(0),"salearaname"));
                jopresentatives.put("userinfoid__jobnumber", getMapValue(presentatives.get(0),"userinfoid__jobnumber"));
                jopresentatives.put("positionname", getMapValue(presentatives.get(0),"positionname"));
                jopresentatives.put("orgstructtypeid", getMapValue(presentatives.get(0),"orgstructtypeid"));
                jopresentatives.put("userinfoid__email", getMapValue(presentatives.get(0),"userinfoid__email"));
                jopresentatives.put("accountcode", getMapValue(presentatives.get(0),"accountcode"));
                jopresentatives.put("membername", getMapValue(presentatives.get(0),"membername"));
                jopresentatives.put("status", getMapValue(presentatives.get(0),"status"));
//如果value为null，将其置为""
                for(String s:jopresentatives.keySet()){
                    if(null==jopresentatives.get(s)){
                        jopresentatives.put(s,"");
                    }
                }

                String mapStringjopresentatives = JSON.toJSONString(jopresentatives, SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty);
                JSONObject jo2jopresentatives = JSON.parseObject(mapStringjopresentatives);
                JSONArray jsonjopresentatives = new JSONArray();
                jsonjopresentatives.add(jo2jopresentatives);
                //更新负责人字段
                HashMap paramMappresentatives = new HashMap();
                paramMappresentatives.put("presentative",jsonjopresentatives.toString());
                paramMappresentatives.put("id",storeId);
                personMapper.updatePresentatives(paramMappresentatives);
            }
        }

        long endtime=System.currentTimeMillis();
        System.out.println("结束时间记录："+df2.format(new Date()));
        long l=endtime-starttime;
        long day=l/(24*60*60*1000);
        long hour=(l/(60*60*1000)-day*24);
        long min=((l/(60*1000))-day*24*60-hour*60);
        long s=(l/1000-day*24*60*60-hour*60*60-min*60);
        System.out.println("单个线程耗时："+day+"天"+hour+"小时"+min+"分"+s+"秒");
    }

    //检查map的key是否为空
    public static String getMapValue(Map map,String key) {
        if(map.containsKey(key)){
            return String.valueOf(map.get(key));
        }else {
            return "";
        }
    }
//取随机数
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
    //返回手机号码
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    private static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }

    //获取现在时间,返回时间戳字符串格式 yyyy-MM-dd HH:mm:ss
    public static Timestamp getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Timestamp ts = Timestamp.valueOf(dateString);
        return ts;
    }

    //随机取经纬度
    public String randomLonLat(double MinLon, double MaxLon) {
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        String lonOrlat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();// 小数后6位
        return lonOrlat;
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomCommon(int min, int max, int n){
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while(count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if(num == result[j]){
                    flag = false;
                    break;
                }
            }
            if(flag){
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}


