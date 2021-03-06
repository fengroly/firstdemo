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
        System.out.println("???????????????");
    }

    //?????????????????????
    @Test
    void testCreateIndex() throws IOException {
        //??????????????????
        CreateIndexRequest request = new CreateIndexRequest("fg_index");
        //????????????
        client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println("123");
    }
    //??????????????????
    @Test
    void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("fg_index");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    //?????????
    @Test
    void testDelete() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("fg_index");
        //??????
        AcknowledgedResponse delete = client.indices().delete(request,RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    //??????????????????
    @Test
    void testAddDocument() throws IOException {
        ESUser user = new ESUser("??????",17);
        IndexRequest request = new IndexRequest("fg_index");
        request.id("1");
        request.timeout("1s");
        //?????????????????????
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //?????????????????????
        IndexResponse indexResponse=client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());//????????????????????????????????????
    }
    //?????????????????????????????????
    @Test
    void testExists() throws IOException {
        GetRequest request = new GetRequest("fg_index","1");
        //??????????????????_soure????????????
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    //??????????????????
    @Test
    void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("fg_index","1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());//?????????????????????
        System.out.println(response);
    }
    //??????????????????
    @Test
    void testUpdate() throws IOException {
        UpdateRequest request = new UpdateRequest("fg_index","1");
        request.timeout("1s");
        ESUser user = new ESUser("??????",19);
        request.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }
    //??????????????????
    @Test
    void testDocDelete() throws IOException {
/*		DeleteResponse response = client.prepareDelete("company", "employee", "1").get();
		System.out.println(response.getResult());*/
		/*DeleteRequest request = new DeleteRequest("xu_index","2");
		request.timeout("1s");
		DeleteResponse deleteResponse = client.delete(request,RequestOptions.DEFAULT);
		System.out.println(deleteResponse.status());*/
    }

    //??????????????????
    @Test
    void testBulkReuest() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        List<ESUser> userList = new ArrayList<>();
        userList.add(new ESUser("??????0", 18));
        userList.add(new ESUser("??????1", 18));
        userList.add(new ESUser("??????2", 18));
        userList.add(new ESUser("??????3", 18));
        userList.add(new ESUser("??????4", 18));
        userList.add(new ESUser("??????5", 18));
        userList.add(new ESUser("??????6", 18));
        userList.add(new ESUser("??????7", 18));
        userList.add(new ESUser("??????8", 18));
        System.out.println(userList);
        for ( int i = 0;i<userList.size();i++){
            bulkRequest.add(new IndexRequest("test2").id(""+(i+1)).source(JSON.toJSONString(userList.get(i)),XContentType.JSON));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());//??????????????????

    }
    //??????
    @Test
    void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("test2");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());//????????????
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("0", "name"));//????????????
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("==============================");
        System.out.println("?????????"+searchResponse.getHits().getTotalHits().value);
        for (SearchHit DFile: searchResponse.getHits().getHits() ) {
            System.out.println(DFile.getSourceAsMap());
        }

    }
    //?????????????????????
    @Test
    void test1Search() throws IOException {
        //???????????????
        SearchRequest searchRequest = new SearchRequest("xu_index");
        //??????????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //??????????????????????????????????????????????????????
        //searchSourceBuilder.from(0).size(3);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());//????????????
        //searchSourceBuilder.query(QueryBuilders.multiMatchQuery("??????", "name"));//????????????
        //??????score??????(????????????)
        //searchSourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.ASC));
        //??????id??????????????????
        searchSourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));
        //??????????????????
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //????????????
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println("==============================");
        System.out.println("?????????"+searchResponse.getHits().getTotalHits().value);
        for (SearchHit DFile: searchResponse.getHits().getHits() ) {
            System.out.println(DFile.getSourceAsMap());
        }

    }

    //???????????????
    @Test
    public void  testCUD() throws IOException {
        //?????????
        BulkRequest bulkRequest = new BulkRequest();
        //????????????
        List<ESUser> userList = new ArrayList<>();
        userList.add(new ESUser("??????0", 18));
        userList.add(new ESUser("??????1", 18));
        userList.add(new ESUser("??????2", 18));
        userList.add(new ESUser("??????3", 18));
        userList.add(new ESUser("??????4", 18));
        userList.add(new ESUser("??????5", 18));
        userList.add(new ESUser("??????6", 18));
        userList.add(new ESUser("??????7", 18));
        userList.add(new ESUser ("??????8", 18));
        System.out.println(userList);
        for ( int i = 0;i<userList.size();i++){
            bulkRequest.add(
                    new IndexRequest("test2").id(""+(i+1))
                            .source(JSON.toJSONString(userList.get(i)),XContentType.JSON)
            );
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.hasFailures());//??????????????????
    }

    //------------------------------------------------------------------------------------------------------
    @Test
    public void simpleRead() {
        // ????????????????????? DemoDataListener ?????????spring????????????????????????excel??????new,??????????????????spring???????????????????????????
        // ??????1???
        String fileName = "C:\\Users\\Administrator\\Desktop\\demo.xlsx";
        // ?????? ????????????????????????class??????????????????????????????sheet ????????????????????????
        EasyExcel.read(fileName, ExcelUser.class, new ExcelListener()).sheet().doRead();

        // ??????2???
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
//        ExcelReader excelReader = null;
//        try {
//            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
//            ReadSheet readSheet = EasyExcel.readSheet(0).build();
//            excelReader.read(readSheet);
//        } finally {
//            if (excelReader != null) {
//                // ???????????????????????????????????????????????????????????????????????????????????????
//                excelReader.finish();
//            }
//        }
    }

    @Test
    public void simpleWrite () {
        List<ExcelUser> excelUsers = new ArrayList<>();
        excelUsers.add(new ExcelUser("??????1",new Date(),1.111));
        excelUsers.add(new ExcelUser("??????2",new Date(),1.211));
        excelUsers.add(new ExcelUser("??????3",new Date(),1.311));
        excelUsers.add(new ExcelUser("??????4",new Date(),1.411));

        String fileName =  "C:\\Users\\Administrator\\Desktop\\demo.xlsx";
        EasyExcel.write(fileName, ExcelUser.class).sheet("??????").doWrite(excelUsers);
    }
}
