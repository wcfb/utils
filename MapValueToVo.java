package wcfb.utils;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapValueToVo {
	
	/**
	 * 
	 * @param clss  ����Ҫ�����Vo��
	 * @param source  ����reqeust.getParameterMap()
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <E> E requestMapValueToVo(Class<E> clss,Map<String,String[]> source) throws InstantiationException, IllegalAccessException {
		//������mapת����Map<String,String>
		Map<String,Object> converMap = new HashMap<String, Object>();
		
		for(Map.Entry<String, String[]> entry:source.entrySet()){
			//��value����Ϊ1�����ݴ浽coverMap�У��ж��ֵ��value������
			if(entry.getValue()!=null && entry.getValue().length==1){
				converMap.put(entry.getKey(), entry.getValue()[0]);
			}
			
		}
		System.out.println("ת�����Map:"+converMap);
		return mapValueToVo(clss, converMap);
		
	}
	
	
	//�Զ�����  �˽���ʵ��ԭ��  : Spring Hibernate   Servlet  Tomcat
	public static <E> E mapValueToVo(Class<E> clss,Map<String,Object> source) throws InstantiationException, IllegalAccessException{
		E  vo = clss.newInstance();//�����޲εĹ��췽��
		Method[] mList = clss.getMethods();//XML    ע�⡣��
		for (int i = 0; i < mList.length; i++) {
			Method m = mList[i];//���е�Method
			String methodName =  m.getName();//�õ���������
			if (methodName.startsWith("set")){//ֻ������set��ͷ����,�Ҳ����б�Ϊ1����
				
				Class[] paramTypes =  m.getParameterTypes();
				if (paramTypes.length==1){
					String paramType = paramTypes[0].getName();
				//	
					//���ݷ������ƣ���hashMap��ȡ����Ӧ�����ݣ�ȥ����������set�ַ�������set�ַ���ĵ�һ����ĸ�ĳ�Сд��
					methodName = methodName.substring(3);
					methodName = methodName.substring(0, 1).toLowerCase()+methodName.substring(1);
					
					/**
					 * source.put("employeeId", 888);Object mapValues =888 //Integer
		source.put("name", "��");   Object mapValues = "��"  String
		source.put("gender","��");
		source.put("manager","��Ŀ����");
		source.put("salary", 99);
		source.put("birthday","2018-01-09");//JDBC
					 */
					Object mapValues =  source.get(methodName);//���ݴ����ķ�������ȡ��map�еĶ�Ӧֵ
					String mapValuesType = "";
					if (mapValues!=null){ //mapValues instanceOf xxxx
						mapValuesType = mapValues.getClass().getName();//����+����
					}
					
					//����set�������������ͣ�������Դ��ȡ������ֵת���ɲ���������������ͣ��ٵ���invoke����
					try {
						if (mapValues!=null){
								if (paramType.equals("java.lang.String")){//��������Ĳ�������Ϊ�ַ�������mapValuesת���ַ���
									m.invoke(vo, String.valueOf(mapValues));
									//m.invoke(vo, mapValues);
								}else if (paramType.equals("java.util.Date")){//������������ΪDateʱ����mapValuesת����Date
									//���ж�mapValues��ʵ������
									//�ж�mapValues��ʵ�����������������ȫƥ��ʱ��ֱ�ӵ��ü���
									//if (mapValuesType.equals("java.util.Date"))
									if (mapValues instanceof Date){
										m.invoke(vo, mapValues);
									}else if (mapValues instanceof String){
										//���ַ���ת��������
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										Date d = sdf.parse(String.valueOf(mapValues));
										m.invoke(vo, d);
									}else if (mapValues instanceof Timestamp){
										//��Timestampת��date;
										m.invoke(vo, mapValues);
									}
									
								}else if (paramType.equals("java.lang.Double")|| paramType.equals("double")){
									//�ж�map�е�ֵ������ַ��������ַ���ת����double
									if (mapValues instanceof String){
										if (String.valueOf(mapValues).matches("[0-9]+[.]?[0-9]*")){
											m.invoke(vo, Double.parseDouble(String.valueOf(mapValues)));
										}
									}else if (mapValues instanceof Double){
										m.invoke(vo, mapValues);
									}else{
										m.invoke(vo, Double.parseDouble(String.valueOf(mapValues)));
									}
								}else if (paramType.equals("java.lang.Integer")|| paramType.equals("int")){
									m.invoke(vo, Integer.parseInt(String.valueOf(mapValues)));
									//else if   //�жϰ��ֻ����������ͼ����װ���ͣ���ת�����ٵ���invoke����
								}// ����  ����  ����
						
						}
					//	m.invoke(vo, mapValues);//����Method����ʾ��EmployeeVo�еķ���,��map��ȡ�����Ķ�Ӧֵ�浽vo��
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
		return vo;
	}
	
	
}
