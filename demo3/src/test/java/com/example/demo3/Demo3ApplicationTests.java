package com.example.demo3;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.example.demo3.listener.ExcelListener;
import com.example.demo3.model.ESUser;
import com.example.demo3.model.ExcelUser;
import com.example.demo3.model.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class Demo3ApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;

    @Test
    void contextLoads() {
        System.out.println("冯广最帅！");
    }

    //测试索引的创建
    @Test
    void testCreateIndex() throws IOException {
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("fg_index");
        //执行请求
        client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("123");
    }
    //测试获取索引
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("fg_index");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    //删除库
    @Test
    void testDelete() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("fg_index");
        //删除
        AcknowledgedResponse delete = client.indices().delete(request,RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    //测试添加文档
    @Test
    void testAddDocument() throws IOException {
        ESUser user = new ESUser("张三",17);
        IndexRequest request = new IndexRequest("fg_index");
        request.id("1");
        request.timeout("1s");
        //将数据放入请求
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse=client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());//对应我们的命令返回的状态
    }
    //获取文档，判断是否存在
    @Test
    void testExists() throws IOException {
        GetRequest request = new GetRequest("fg_index","1");
        //不获取返回的_soure的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    //获取文档信息
    @Test
    void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("fg_index","1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());//打印文档的内容
        System.out.println(response);
    }
    //更新文档信息
    @Test
    void testUpdate() throws IOException {
        UpdateRequest request = new UpdateRequest("fg_index","1");
        request.timeout("1s");
        ESUser user = new ESUser("李四",19);
        request.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }
    //删除文档记录
    @Test
    void testDocDelete() throws IOException {
/*		DeleteResponse response = client.prepareDelete("company", "employee", "1").get();
		System.out.println(response.getResult());*/
		/*DeleteRequest request = new DeleteRequest("xu_index","2");
		request.timeout("1s");
		DeleteResponse deleteResponse = client.delete(request,RequestOptions.DEFAULT);
		System.out.println(deleteResponse.status());*/
    }

    //批量插入数据
    @Test
    void testBulkReuest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<ESUser> userList = new ArrayList<>();
        userList.add(new ESUser("张三0", 18));
        userList.add(new ESUser("张三1", 18));
        userList.add(new ESUser("张三2", 18));
        userList.add(new ESUser("张三3", 18));
        userList.add(new ESUser("张三4", 18));
        userList.add(new ESUser("张三5", 18));
        userList.add(new ESUser("张三6", 18));
        userList.add(new ESUser("张三7", 18));
        userList.add(new ESUser("张三8", 18));
        System.out.println(userList);
        for ( int i = 0;i<userList.size();i++){
            bulkRequest.add(new IndexRequest("test2").id(""+(i+1)).source(JSON.toJSONString(userList.get(i)),XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());//判断是否失败

    }
    //查询
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test2");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());//查询所有
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("0", "name"));//匹配查询
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("==============================");
        System.out.println("总条数"+searchResponse.getHits().getTotalHits().value);
        for (SearchHit DFile: searchResponse.getHits().getHits() ) {
            System.out.println(DFile.getSourceAsMap());
        }

    }
    //分页查询，排序
    @Test
    void test1Search() throws IOException {
        //指定索引库
        SearchRequest searchRequest = new SearchRequest("xu_index");
        //构造查询对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //添加分页条件，从第几个开始，返回几个
        //searchSourceBuilder.from(0).size(3);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());//查询所有
        //searchSourceBuilder.query(QueryBuilders.multiMatchQuery("张三", "name"));//匹配查询
        //按照score排序(默认倒序)
        //searchSourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.ASC));
        //根据id排序（倒序）
        searchSourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));
        //设置执行时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //执行请求
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("==============================");
        System.out.println("总条数"+searchResponse.getHits().getTotalHits().value);
        for (SearchHit DFile: searchResponse.getHits().getHits() ) {
            System.out.println(DFile.getSourceAsMap());
        }

    }

    //批量增删改
    @Test
    public void  testCUD() throws IOException {
        //初始化
        BulkRequest bulkRequest = new BulkRequest();
        //批量增加
        List<ESUser> userList = new ArrayList<>();
        userList.add(new ESUser("张三0", 18));
        userList.add(new ESUser("张三1", 18));
        userList.add(new ESUser("张三2", 18));
        userList.add(new ESUser("张三3", 18));
        userList.add(new ESUser("张三4", 18));
        userList.add(new ESUser("张三5", 18));
        userList.add(new ESUser("张三6", 18));
        userList.add(new ESUser("张三7", 18));
        userList.add(new ESUser ("张三8", 18));
        System.out.println(userList);
        for ( int i = 0;i<userList.size();i++){
            bulkRequest.add(
                    new IndexRequest("test2").id(""+(i+1))
                            .source(JSON.toJSONString(userList.get(i)),XContentType.JSON)
            );
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());//判断是否失败
    }

    //------------------------------------------------------------------------------------------------------
    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "C:\\Users\\Administrator\\Desktop\\demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ExcelUser.class, new ExcelListener()).sheet().doRead();

        // 写法2：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
//        ExcelReader excelReader = null;
//        try {
//            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
//            ReadSheet readSheet = EasyExcel.readSheet(0).build();
//            excelReader.read(readSheet);
//        } finally {
//            if (excelReader != null) {
//                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//                excelReader.finish();
//            }
//        }
    }

    @Test
    public void simpleWrite () {
        List<ExcelUser> excelUsers = new ArrayList<>();
        excelUsers.add(new ExcelUser("冯广1",new Date(),1.111));
        excelUsers.add(new ExcelUser("冯广2",new Date(),1.211));
        excelUsers.add(new ExcelUser("冯广3",new Date(),1.311));
        excelUsers.add(new ExcelUser("冯广4",new Date(),1.411));

        String fileName =  "C:\\Users\\Administrator\\Desktop\\demo.xlsx";
        EasyExcel.write(fileName, ExcelUser.class).sheet("模板").doWrite(excelUsers);
    }
}
