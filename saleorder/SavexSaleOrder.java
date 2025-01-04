package com.kingdee.webapi.javasdk.saleorder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import com.kingdee.webapi.javasdk.common.SeqHelper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.UUID;

import static org.junit.Assert.fail;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SavexSaleOrder {
    private K3CloudApi api = new K3CloudApi();
    private static String FNumber = SeqHelper.genNumber("XSDD");
    private static String FName = "auxsdd_" + UUID.randomUUID();
    private static String saleid = "";

    private String json = "{\n" +
            "        \"NeedUpDateFields\": [],\n" +
            "        \"NeedReturnFields\": [],\n" +
            "        \"IsDeleteEntry\": \"true\",\n" +
            "        \"SubSystemId\": \"\",\n" +
            "        \"IsVerifyBaseDataField\": \"False\",\n" +
            "        \"IsEntryBatchFill\": \"true\",\n" +
            "        \"ValidateFlag\": \"true\",\n" +
            "        \"NumberSearch\": \"true\",\n" +
            "        \"IsAutoAdjustField\": \"False\",\n" +
            "        \"InterationFlags\": \"\",\n" +
            "        \"IgnoreInterationFlag\": \"\",\n" +
            "        \"IsControlPrecision\": \"False\",\n" +
            "        \"Model\": {\n" +
            "    \"FBillTypeID\": {\n" +
            "      \"FNUMBER\": \"XSDD01_SYS\"\n" +
            "    },\n" +
            "    \"FDate\": \"2022-04-27 00:00:00\",\n" +
            "    \"FSaleOrgId\": {\n" +
            "      \"FNumber\": \"100\"\n" +
            "    },\n" +
            "    \"FCustId\": {\n" +
            "      \"FNumber\": \"SCMKH100001\"\n" +
            "    },\n" +
            "    \"FReceiveId\": {\n" +
            "      \"FNumber\": \"SCMKH100001\"\n" +
            "    },\n" +
            "    \"FSaleDeptId\": {\n" +
            "      \"FNumber\": \"SCMBM000001\"\n" +
            "    },\n" +
            "    \"FSalerId\": {\n" +
            "      \"FNumber\": \"SCMYG000001_SCMGW000001_1\"\n" +
            "    },\n" +
            "    \"FSettleId\": {\n" +
            "      \"FNumber\": \"SCMKH100001\"\n" +
            "    },\n" +
            "    \"FChargeId\": {\n" +
            "      \"FNumber\": \"SCMKH100001\"\n" +
            "    },\n" +
            "    \"FSaleOrderFinance\": {\n" +
            "      \"FSettleCurrId\": {\n" +
            "        \"FNumber\": \"PRE001\"\n" +
            "      },\n" +
            "      \"FIsPriceExcludeTax\": 'true',\n" +
            "      \"FIsIncludedTax\": 'true',\n" +
            "      \"FExchangeTypeId\": {\n" +
            "        \"FNumber\": \"HLTX01_SYS\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"FSaleOrderEntry\": [\n" +
            "      {\n" +
            "        \"FRowType\": \"Standard\",\n" +
            "        \"FMaterialId\": {\n" +
            "          \"FNumber\": \"SCMWL100002\"\n" +
            "        },\n" +
            "        \"FUnitID\": {\n" +
            "          \"FNumber\": \"Pcs\"\n" +
            "        },\n" +
            "        \"FQty\": 10,\n" +
            "        \"FPriceUnitId\": {\n" +
            "          \"FNumber\": \"Pcs\"\n" +
            "        },\n" +
            "        \"FPrice\": 8.849558,\n" +
            "        \"FTaxPrice\": 10,\n" +
            "        \"FEntryTaxRate\": 13,\n" +
            "        \"FDeliveryDate\": \"2022-04-27 15:15:54\",\n" +
            "        \"FStockOrgId\": {\n" +
            "          \"FNumber\": \"100\"\n" +
            "        },\n" +
            "        \"FSettleOrgIds\": {\n" +
            "          \"FNumber\": \"100\"\n" +
            "        },\n" +
            "        \"FSupplyOrgId\": {\n" +
            "          \"FNumber\": \"100\"\n" +
            "        },\n" +
            "        \"FOwnerTypeId\": \"BD_OwnerOrg\",\n" +
            "        \"FOwnerId\": {\n" +
            "          \"FNumber\": \"100\"\n" +
            "        },\n" +
            "        \"FReserveType\": \"1\",\n" +
            "        \"FPriceBaseQty\": 10,\n" +
            "        \"FStockUnitID\": {\n" +
            "          \"FNumber\": \"Pcs\"\n" +
            "        },\n" +
            "        \"FStockQty\": 10,\n" +
            "        \"FStockBaseQty\": 10,\n" +
            "        \"FOUTLMTUNIT\": \"SAL\",\n" +
            "        \"FOutLmtUnitID\": {\n" +
            "          \"FNumber\": \"Pcs\"\n" +
            "        },\n" +
            "        \"FAllAmountExceptDisCount\": 100,\n" +
            "        \"FOrderEntryPlan\": [\n" +
            "          {\n" +
            "            \"FPlanDate\": \"2022-04-27 15:15:54\",\n" +
            "            \"FPlanQty\": 10\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"FSaleOrderPlan\": [\n" +
            "      {\n" +
            "        \"FRecAdvanceRate\": 100,\n" +
            "        \"FRecAdvanceAmount\": 100\n" +
            "      }\n" +
            "    ],\n" +
            "    \"FBillNo\":"+"\""+FNumber+"\""+",\n" +
            "  }\n" +
            "    }";


    /* 本接口用于实现销售订单 (SAL_SaleOrder)的保存功能 */
    @Test
    public void atestSaveSaleOrder() throws Exception {
        String result = api.save("SAL_SaleOrder", json);
        Gson gson = new Gson();
        RepoRet sRet = gson.fromJson(result, RepoRet.class);
        if (sRet.isSuccessfully()) {
            saleid = sRet.getResult().getId();
            System.out.printf("销售订单保存接口%s%n", gson.toJson(sRet.getResult()));
        } else {
            fail("销售订单保存接口: " + gson.toJson(sRet.getResult()));
        }
    }


    /*本接口用于实现销售订单 (SAL_SaleOrder)的提交功能*/
    @Test
    public void btestSubmitSaleOrder() throws Exception {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Numbers", FNumber);
        String result = api.submit("SAL_SaleOrder", jsonData.toString());
        Gson gson = new Gson();
        RepoRet repoRet = gson.fromJson(result, RepoRet.class);
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("销售订单提交接口: %s%n", gson.toJson(repoRet.getResult()));
        } else {
            fail("销售订单提交接口: " + gson.toJson(repoRet.getResult()));
        }
    }

    /*本接口用于实现销售订单 (SAL_SaleOrder)的审核功能*/
    @Test
    public void ctestauditSaleOrder() throws Exception {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("Numbers", FNumber);
        String result = api.audit("SAL_SaleOrder", jsonData.toString());
        Gson gson = new Gson();
        RepoRet repoRet = gson.fromJson(result, RepoRet.class);
        if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
            System.out.printf("销售订单审核接口: %s%n", gson.toJson(repoRet.getResult()));
        } else {
            fail("销售订单审核接口: " + gson.toJson(repoRet.getResult()));
        }
    }

    //自定义api测试
    @Test
    public void utestApi() {
        try {
            K3CloudApi api = new K3CloudApi();
            Boolean testResult=api.CheckAuthInfo().getResponseStatus().isIsSuccess();
            if(testResult) {
                var requestUrl = "Kingdee.K3.SCM.WebApi.ServicesStub.SaveXSaleOrderWebApi.SaveXSaleOrder";
                Object[] paramInfo = new Object[]{"{\"SaleOrderBillNo\": "+"\""+FNumber+"\""+",\"SaleOrderBillId\":"+saleid+",}"};
                String result = String.valueOf(api.execute(requestUrl, paramInfo));
                JsonElement jsondata = new JsonParser().parse(result);
                boolean issucess = jsondata.getAsJsonObject().get("IsSuccess").getAsBoolean();
                if(issucess){
                    System.out.println("销售订单新变更单：" + result);
                }
                else {
                    fail("销售订单新变更单：" + result);
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
