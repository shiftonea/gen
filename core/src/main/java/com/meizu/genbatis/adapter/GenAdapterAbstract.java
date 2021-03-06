/*
 * Copyright (c) 2017. tengqingya@meizu.com qq:475804848 dingding:taizhoujiangyan.
 */

package com.meizu.genbatis.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meizu.genbatis.adaptee.AdapteeInterface;
import com.meizu.genbatis.model.HtmlTemplate;
import com.meizu.genbatis.target.TargetGenInterface;
import com.meizu.genbatis.util.FreeMarkers;
import com.meizu.genbatis.util.ListUtil;
import com.meizu.genbatis.util.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 生成button适配器
 *
 * @author tengqingya
 * @create 2017-01-20 14:56
 */
public abstract class GenAdapterAbstract implements TargetGenInterface,AdapteeInterface<JSONObject> {
    @Override
    public void genTemplate( String config ) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        JSONObject jsonObject = JSON.parseObject(config);
        Validate.notEmpty(jsonObject, "参数不能为空");
        //bean属性和jsonobject的时候使用name="${l.id}"或者name="${item["id"]}"都可以
//        button((List<JSONObject>)jsonObject.get("button"),"button");
//        checkbox((List<JSONObject>)jsonObject.get("checkbox"),"checkbox");
        Class<AdapteeInterface> adapteeInterfaceClass = AdapteeInterface.class;
        String key;
        Object value;
        for( Map.Entry<String, Object> entry : jsonObject.entrySet() ) {
            key = entry.getKey();
            value = entry.getValue();
            Method method = adapteeInterfaceClass.getMethod(key, List.class, String.class, String.class);
            method.invoke(this, value, key, "/template/js/");
            method.invoke(this, value, key, "/template/html/");
        }
        gen(null);
    }

    @Override
    public void button( List<JSONObject> list, String fileName ,String prefix) {
        genTemplate(list, fileName,prefix);
    }

    @Override
    public void checkbox( List<JSONObject> list, String fileName,String prefix ) {
        genTemplate(list, fileName,prefix);
    }

    @Override
    public void radio(List<JSONObject> list, String fileName,String prefix ){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void datepicker(List<JSONObject> list, String fileName ,String prefix){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void table(List<JSONObject> list, String fileName ,String prefix){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void dropdown(List<JSONObject> list, String fileName,String prefix ){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void fileupload(List<JSONObject> list, String fileName,String prefix ){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void input(List<JSONObject> list, String fileName,String prefix ){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void inserttablejs(List<JSONObject> list, String fileName,String prefix ){
        genTemplate(list,fileName,prefix);
    }

    @Override
    public void modal(List<JSONObject> list, String fileName,String prefix ){
        genTemplate(list,fileName,prefix);
    }

    private void genTemplate( List<JSONObject> list, String fileName ,String prefix) {
//        Validate.notEmpty(list, "集合非空");
        if( ListUtil.isEmpty(list)){
            return;
        }
        HtmlTemplate template = XmlUtil.fileToObject(prefix + fileName + ".xml", HtmlTemplate.class);
        if( template == null){
            return;
        }
        String s;
        for( JSONObject o : list ) {
            s = FreeMarkers.renderString(template.getContent(), o);
            System.out.println(s);
        }
    }

    public abstract <T> void gen(List<T> list);
}
