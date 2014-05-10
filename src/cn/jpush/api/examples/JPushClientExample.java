package cn.jpush.api.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.report.ReceivedsResult;

public class JPushClientExample {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushClientExample.class);

    // demo App defined in resources/jpush-api.conf 
	private static final String appKey ="dd1066407b044738b6479275";
	private static final String masterSecret = "2b38ce69b1de2a7fa95706ea";
	
	public static final String TITLE = "Test from API example";
    public static final String CONTENT = "Test from Javen";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";

	public static void main(String[] args) {
	    testSendNotification();
//	    testSendMesasge();
		testGetReport();
	}
	
	private static void testSendNotification() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.alertAll(CONTENT);
        LOG.info("Paylaod JSON - " + payload.toString());
        
        PushResult result = jpushClient.sendPush(payload);
        if (result.isResultOK()) {
            LOG.debug(result.toString());
        } else {
            if (result.getErrorCode() > 0) {
                LOG.warn(result.getOriginalContent());
            } else {
                LOG.debug("Maybe connect error. Retry laster. ");
            }
        }
	}
	
    private static void testSendMesasge() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.messageAll(CONTENT);
        jpushClient.sendPush(payload);
    }
    
	public static void testGetReport() {
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		ReceivedsResult receivedsResult = jpushClient.getReportReceiveds("1708010723,1774452771");
        LOG.debug("responseContent - " + receivedsResult.getOriginalContent());
		if (receivedsResult.isResultOK()) {
		    LOG.info("Receiveds - " + receivedsResult);
		} else {
            if (receivedsResult.getErrorCode() > 0) {
                // 业务异常
                LOG.warn("Service error - ErrorCode: "
                        + receivedsResult.getErrorCode() + ", ErrorMessage: "
                        + receivedsResult.getErrorMessage());
            } else {
                // 未到达 JPush
                LOG.error("Other excepitons - "
                        + receivedsResult.getExceptionString());
            }
		}
	}

}

