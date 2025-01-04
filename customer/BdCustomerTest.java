package com.kingdee.webapi.javasdk.customer;

import static org.junit.Assert.fail;

import java.util.*;

import com.google.gson.JsonObject;
import com.kingdee.bos.webapi.entity.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.webapi.javasdk.common.SeqHelper;
import com.google.gson.Gson;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BdCustomerTest {
    private static String FNumber = SeqHelper.genNumber("BC");
    private static String FNumber2 = SeqHelper.genNumber("BC");
    private static String FName = "aukh_" + UUID.randomUUID();
    private static String FShortName = "aukh_" + UUID.randomUUID();
    private static K3CloudApi api = new K3CloudApi();


    /* 本接口用于实现客户 (BD_Customer) 的保存功能 */
    @Test
    public void atestSaveCustomer() throws Exception {
        String para = "{\"FName\":  "+"\""+FName+"\""+",\"FNumber\": "+"\""+FNumber+"\""+",\"FCreateOrgId\": {\"FNumber\": \"100\"},\"FUseOrgId\": {\"FNumber\": \"100\"},\"FCOUNTRY\": {    \"FNumber\": \"China\"},\"FINVOICETITLE\": \"zzl\",\"FCustTypeId\": {    \"FNumber\": \"KHLB001_SYS\"},\"FTRADINGCURRID\": {    \"FNumber\": \"PRE001\"},\"FInvoiceType\": \"1\",\"FTaxType\": {    \"FNumber\": \"SFL02_SYS\"},\"FPriority\": 1,\"FTaxRate\": {    \"FNumber\": \"SL02_SYS\"},\"FISCREDITCHECK\": true,\"FIsTrade\": true    }";
        Map<String, Object> map = new HashMap<>();
        map = new Gson().fromJson(para, map.getClass());
        SaveResult sRet = api.save("BD_Customer", new SaveParam<Map>(map));
        var gson = new Gson();
        if (sRet.isSuccessfully()) {
            System.out.printf("客户保存接口: %s%n", gson.toJson(sRet.getResult()));
        } else {
            fail("客户保存接口: " + gson.toJson(sRet.getResult()));
        }
    }

    /* 本接口用于实现客户 (BD_Customer) 的保存功能 */
    @Test
    public void btestSaveCustomer2() throws Exception {
        String para2 = "{\"FName\": \"zzl\",\"FNumber\": "+"\""+FNumber2+"\""+",\"FCreateOrgId\": {\"FNumber\": \"100\"},\"FUseOrgId\": {\"FNumber\": \"100\"},\"FCOUNTRY\": {    \"FNumber\": \"China\"},\"FINVOICETITLE\": \"zzl\",\"FCustTypeId\": {    \"FNumber\": \"KHLB001_SYS\"},\"FTRADINGCURRID\": {    \"FNumber\": \"PRE001\"},\"FInvoiceType\": \"1\",\"FTaxType\": {    \"FNumber\": \"SFL02_SYS\"},\"FPriority\": 1,\"FTaxRate\": {    \"FNumber\": \"SL02_SYS\"},\"FISCREDITCHECK\": true,\"FIsTrade\": true    }";
        Map<String, Object> map = new HashMap<>();
        map = new Gson().fromJson(para2, map.getClass());
        SaveResult sRet = api.save("BD_Customer", new SaveParam<Map>(map),InvokeMode.Syn);
        Gson gson = new Gson();
        if (sRet.isSuccessfully()) {
            System.out.printf("客户保存接口: %s%n", gson.toJson(sRet.getResult()));
        } else {
            fail("客户保存接口: " + gson.toJson(sRet.getResult()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的提交功能*/
    @Test
    public void ctestSubmitCustomer() throws Exception {
        OperateParam para = new OperateParam();
        List<String> Numbers = List.of(FNumber);
        para.setNumbers(Numbers);
        OperatorResult result = api.submit("BD_Customer", para);
        Gson gson = new Gson();
         if (result.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户提交接口: %s%n", gson.toJson(result.getResult()));
        } else {
            fail("客户提交接口：" + gson.toJson(result.getResult()));
        }

    }

    /*本接口用于实现客户 (BD_Customer) 的审核功能*/
    @Test
    public void dtestauditCustomer() throws Exception {
        var para = new OperateParam();
        List<String> Numbers = List.of(FNumber);
        para.setNumbers(Numbers);
        OperatorResult result = api.audit("BD_Customer", para );
        Gson gson = new Gson();
        if (result.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户审核接口: %s%n", gson.toJson(result.getResult()));
        } else {
            fail("客户审核接口: " + gson.toJson(result.getResult().getResponseStatus()));
        }

    }

    /*本接口用于实现客户 (BD_Customer) 的反审核功能*/
    @Test
    public void etestunauditCustomer() throws Exception {
        var para = new OperateParam();
        List<String> Numbers = List.of(FNumber);
        para.setNumbers(Numbers);
        OperatorResult result = api.unAudit("BD_Customer", para);
        Gson gson = new Gson();
        if (result.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户反审核接口: %s%n", gson.toJson(result.getResult()));
        } else {
            fail("客户反审核接口: " + gson.toJson(result.getResult().getResponseStatus()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的禁用功能*/
    @Test
    public void ftestForbidCustomer() throws Exception {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Numbers", FNumber);
        String result = api.excuteOperation("BD_Customer","Forbid", jsonData.toString());
        Gson gson = new Gson();
        RepoRet repoRet = gson.fromJson(result, RepoRet.class);
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户禁用接口: %s%n", gson.toJson(repoRet.getResult()));
        } else {
            fail("客户禁用接口: " + gson.toJson(repoRet.getResult().getResponseStatus()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的反禁用功能*/
    @Test
    public void gtestenableCustomer() throws Exception {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Numbers", FNumber);
        String result = api.excuteOperation("BD_Customer","enable", jsonData.toString());
        Gson gson = new Gson();
        RepoRet repoRet = gson.fromJson(result, RepoRet.class);
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户反禁用接口: %s%n", gson.toJson(repoRet.getResult()));
        } else {
            fail("客户反禁用接口: %s%n "+ gson.toJson(repoRet.getResult().getResponseStatus()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的查看功能*/
    @Test
    public void htestviewCustomer() throws Exception {
        var para = new OperateParam();
        para.setNumber(FNumber);
        OperatorResult result = api.view("BD_Customer", para);
        Gson gson = new Gson();
        if (result.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户查看接口: %s%n", gson.toJson(result.getResult()));
        } else {
            fail("客户查看接口: " + gson.toJson(result.getResult()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的查看功能*/
    @Test
    public void htestviewCustomer2() throws Exception {
        var para = new OperateParam();
        para.setNumber(FNumber);
        OperatorResult<BdCustomer> result = api.view("BD_Customer", para, BdCustomer.class);
        Gson gson = new Gson();
        System.out.printf("result: %s%n", gson.toJson(result));
        if (result.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户查看接口: %s%n", gson.toJson(result.getResult()));
        } else {
            fail("客户查看接口: " + gson.toJson(result.getResult()));
        }
    }

    /*本接口用于实现客户 (BD_Customer)的单据查询功能*/
    @Test
    public void itestQueryCustomer() throws Exception {
        var para = new QueryParam();
        para.setFormId("BD_Customer");
        para.setFieldKeys("FNumber,FName,");
        para.setFilterString("Fnumber=\'"+FNumber+"\'");
        Gson gson = new Gson();
        List result = api.executeBillQuery(para,BdCustomer.class);
        System.out.println("客户单据查询接口: " + gson.toJson(result));
    }

    /*本接口用于实现客户 (BD_Customer) 的删除功能*/
    @Test
    public void jtestdeleteCustomer() throws Exception {
        var para = new OperateParam();
        List<String> Numbers = List.of(FNumber);
        para.setNumbers(Numbers);
        OperatorResult result = api.delete("BD_Customer",para);
        Gson gson = new Gson();
        if (result.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("客户删除接口: %s%n", gson.toJson(result.getResult()));
        } else {
            fail("客户删除接口: " + gson.toJson(result.getResult().getResponseStatus()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的批量提交功能*/
    @Test
    public void kbatchsaveCustomer() throws Exception {
        String para = "[{\"FName\": "+"\""+"aukh_" + UUID.randomUUID().toString()+"\""+",\"FNumber\": "+"\""+SeqHelper.genNumber("KH")+"\""+",\"FCreateOrgId\": {\"FNumber\": \"100\"},\"FUseOrgId\": {\"FNumber\": \"100\"},\"FCOUNTRY\": {\"FNumber\": \"China\"},\"FINVOICETITLE\": \"zzl\",\"FCustTypeId\": {\"FNumber\": \"KHLB001_SYS\"},\"FTRADINGCURRID\": {\"FNumber\": \"PRE001\"},\"FInvoiceType\": \"1\",\"FTaxType\": {\"FNumber\": \"SFL02_SYS\"},\"FPriority\": 1,\"FTaxRate\": {\"FNumber\": \"SL02_SYS\"},\"FISCREDITCHECK\": true,\"FIsTrade\": true},{\"FName\": "+"\""+"auwl_" + UUID.randomUUID().toString()+"\""+",\"FNumber\": "+"\""+SeqHelper.genNumber("KH")+"\""+",\"FCreateOrgId\": {\"FNumber\": \"100\"},\"FUseOrgId\": {\"FNumber\": \"100\"},\"FCOUNTRY\": {\"FNumber\": \"China\"},\"FINVOICETITLE\": \"zzl\",\"FCustTypeId\": {\"FNumber\": \"KHLB001_SYS\"},\"FTRADINGCURRID\": {\"FNumber\": \"PRE001\"},\"FInvoiceType\": \"1\",\"FTaxType\": {\"FNumber\": \"SFL02_SYS\"},\"FPriority\": 1,\"FTaxRate\": {\"FNumber\": \"SL02_SYS\"},\"FISCREDITCHECK\": true,\"FIsTrade\": true}]";
        // 批量保存客户信息
        List<Map<String, Object>> list = new ArrayList<>();
        list = new Gson().fromJson(para, list.getClass());
        SaveResult sRet = api.batchSave("BD_Customer", new BatchSave<>(list));
        Gson gson = new Gson();
        if (sRet.isSuccessfully()) {
            System.out.printf("客户批量保存接口: %s%n", gson.toJson(sRet.getResult()));
        } else {
            fail("客户保存接口: " + gson.toJson(sRet.getResult()));
        }
    }

    /*本接口用于实现客户 (BD_Customer) 的批量提交功能2*/
    @Test
    public void lbatchsaveCustomer2() throws Exception {
        String para = "[{\"FName\": "+"\""+"aukh_" + UUID.randomUUID().toString()+"\""+",\"FNumber\": "+"\""+SeqHelper.genNumber("KH")+"\""+",\"FCreateOrgId\": {\"FNumber\": \"100\"},\"FUseOrgId\": {\"FNumber\": \"100\"},\"FCOUNTRY\": {\"FNumber\": \"China\"},\"FINVOICETITLE\": \"zzl\",\"FCustTypeId\": {\"FNumber\": \"KHLB001_SYS\"},\"FTRADINGCURRID\": {\"FNumber\": \"PRE001\"},\"FInvoiceType\": \"1\",\"FTaxType\": {\"FNumber\": \"SFL02_SYS\"},\"FPriority\": 1,\"FTaxRate\": {\"FNumber\": \"SL02_SYS\"},\"FISCREDITCHECK\": true,\"FIsTrade\": true}]";
        // 批量保存客户信息（传请求模式）
        List<Map<String, Object>> list  = new ArrayList<>();
        list = new Gson().fromJson(para, list.getClass());
        SaveResult sRet = api.batchSave("BD_Customer", new BatchSave<>(list),InvokeMode.Syn);
        Gson gson = new Gson();
        if (sRet.isSuccessfully()) {
            System.out.printf("客户批量保存接口: %s%n", gson.toJson(sRet.getResult()));
        } else {
            fail("客户保存接口: " + gson.toJson(sRet.getResult()));
        }
    }

    /*本接口用于获取账套信息*/
    @Test
    public void ngetDataCenters() throws Exception {
        List datacenter = api.getDataCenters();
        Gson gson = new Gson();
        System.out.printf("或则环境账套信息：%s%n",gson.toJson(datacenter));
    }

}
