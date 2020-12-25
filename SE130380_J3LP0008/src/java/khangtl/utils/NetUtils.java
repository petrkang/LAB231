/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.utils;

import java.io.Serializable;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

/**
 *
 * @author Peter
 */
public class NetUtils implements Serializable {
    public static String getResult(String url) {
        String result = null;
        try {
            result = Request.Get(url).setHeader("Accept-Charset", "utf-8").execute().returnContent().asString();
        } catch (Exception e) {
            Logger.getLogger(NetUtils.class.getName()).debug("Error at NetUtils: " + e.getMessage());
        }
        return result;
    }
}
