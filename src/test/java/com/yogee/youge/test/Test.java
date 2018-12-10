package com.yogee.youge.test;

import com.yogee.youge.modules.sys.service.SystemService;

public class Test {
	/**
	 * 获取模糊的手机号码
	 * @param phone2
	 * @return
	 */
	public static String  getPhone(String phone2){
		return   phone2.substring(0,3) + "****" + phone2.substring(7, phone2.length());
	}
	public static String  getEmail(String email){
		if(email.indexOf("@")<-1){
			return  "";
		}
		String emailname=email.substring(0, email.indexOf("@"));
		String emailAddress=email.substring(email.indexOf("@"),email.length());    //后缀名字
		if(emailname.length()>4){
			return emailname.substring(0, emailname.length()-4) +"****"+emailAddress;
		}else{
			return  "****"+emailAddress;
		}

	}
	public static void main(String[] args) throws Exception{
//		String phone2 = "15988888888";
//		System.out.println(getPhone(phone2));
//		String  email="hrccc@yq1012.com";
//		System.out.println(getEmail(email));



//		String str="26221";
//		System.out.println("原文："+str);
//		try{
//			byte[] data=AES256EncryptionUtils.encrypt(str.getBytes());
//			String secript = AES256EncryptionUtils.parseByte2HexStr(data);
//			System.out.println(secript);
//
////			byte [] dd = parseHexStr2Byte("u/eurSaElUujuhd/ouVT2oKx2hKK6UMzcDSXI76w5owtn7HVyH427zy7HTee8g/lwFGkjAVlvx58zgWDSAFaSbYbxjiZccjzVtT1ma69cPzHvGmjoXtHkMZUgkRgS/k0PPTt0o7dvCUgsDL9FmIm0KkuTxx4kIjHeEonxK4yA75UMK1nO33i5dYCz+C4uN4tovoKOadqOE1DItRkP0Kll29fl5lxLNl5ayEcuk32bkQwIfdWp/RWCXM2dfV4J15jkGcEJdXSzuXgaPgJOrvoGPxemq7CuCTdrGB7fLrDXaFwNKfEhZ8er2Y4ba+OPl7S9INxrRMXIYeG3hPU58A+0Au1706yjijzj9kTDLdKRuwufMIlvl+IiEZkJ2xqwnrQh/UmYmcZjvCXE6baPbtHiA61Ri7K0xO4ztX2Bn8cdDmplj5RIG956oo0piysykFgL7e1kbQz88bCX6XV568j3VXFRps5RGHGFVZBn0VYfA/SXJDIKyaozJWz1SW46Cmc3TjrvvUFm71ebv147acsEE/ZLi2co+AV3dJYu2xdh8nds9WAVeMS5THs5uFAVROxLRknlOnVesEpVWKeMNFreuxzvB/4j9iIAx3ncwaN0adMVlIhSlxfvaJ2uhcHf2LkywfTx0/xAOjGWN8KlG/E3jSsffHcmx1Vw5qjn8B/YiKd8b3KImvHMVYocp7G/Q9Is69yjUBcIvyzyyeO1Vfr3fGGkaCMED3+MLosTYfjRJvTTrtcxBXLsDYsARKgDlsyw8ucexIMksKJpFiERse4Pm2+R8CKj9j2it3Iwp77IjOPvwB62H4NzE1C4erqdUJdGcaaMYEwlT2U/YsYKsEQeD4NTW9YLTG1RYsMDZ3v0OTdCWYPBhgQ2HBbId7G4PoQcDfy3AmoYzVdyM7EXH+9pSWwwP9EFjK4vgp8SUg2gZHDv2D4KIqRwM1viV7EXm8Ez/NEHUAreojmRcXGZ2NO68ChK0BBpvBeIR0KzUcN6Sk88NobP0+QhMS44DQCxWpyXnNziWQiKGz8MrFBZvL0O3aY4HgUr8TKYVeBvmq0V5bbdM3Kn8AlIdgBOTRUyuOpw6zeinuK3urh13Iq7hm/kskO0B35fSAEIfhb4t+MRv4VOrZeS5tC7gr5aqrXg7frkanHu/alSKEHoQANvNslmskd3VLHxGMoOfkJv+FUZHBCeXPvOB06n4+YyV7m1tLjP/4hBJXK3uUuNUwtTHfpPUxIPZ3c74cb+BQs2vqWZ0+sB2dTizxGnzRM1bjJt1Yt");
////			parseByte2HexStr(data);
////			String secript = "da141e5bed6556875fc25dac19bf5e6286e7964527e748de1ea08fc4fe0a4819855ed38dfac07494589dee72704f4a13b984049975244d5d8228a8a1a793aed5fb2efdf62f0281bb4eeb18e4843b909f83a8bcfe3c2eb25bbf8d47e4a5ed98fb";
//			String aadd = AES256EncryptionUtils.decrypt(secript);
//			System.out.println(aadd);
//
////			String aa ="u/eurSaElUujuhd/ouVT2oKx2hKK6UMzcDSXI76w5owtn7HVyH427zy7HTee8g/lwFGkjAVlvx58zgWDSAFaSbYbxjiZccjzVtT1ma69cPzHvGmjoXtHkMZUgkRgS/k0PPTt0o7dvCUgsDL9FmIm0KkuTxx4kIjHeEonxK4yA75UMK1nO33i5dYCz+C4uN4tovoKOadqOE1DItRkP0Kll29fl5lxLNl5ayEcuk32bkQwIfdWp/RWCXM2dfV4J15jkGcEJdXSzuXgaPgJOrvoGPxemq7CuCTdrGB7fLrDXaFwNKfEhZ8er2Y4ba+OPl7S9INxrRMXIYeG3hPU58A+0Au1706yjijzj9kTDLdKRuwufMIlvl+IiEZkJ2xqwnrQh/UmYmcZjvCXE6baPbtHiA61Ri7K0xO4ztX2Bn8cdDmplj5RIG956oo0piysykFgL7e1kbQz88bCX6XV568j3VXFRps5RGHGFVZBn0VYfA/SXJDIKyaozJWz1SW46Cmc3TjrvvUFm71ebv147acsEE/ZLi2co+AV3dJYu2xdh8nds9WAVeMS5THs5uFAVROxLRknlOnVesEpVWKeMNFreuxzvB/4j9iIAx3ncwaN0adMVlIhSlxfvaJ2uhcHf2LkywfTx0/xAOjGWN8KlG/E3jSsffHcmx1Vw5qjn8B/YiKd8b3KImvHMVYocp7G/Q9Is69yjUBcIvyzyyeO1Vfr3fGGkaCMED3+MLosTYfjRJvTTrtcxBXLsDYsARKgDlsyw8ucexIMksKJpFiERse4Pm2+R8CKj9j2it3Iwp77IjOPvwB62H4NzE1C4erqdUJdGcaaMYEwlT2U/YsYKsEQeD4NTW9YLTG1RYsMDZ3v0OTdCWYPBhgQ2HBbId7G4PoQcDfy3AmoYzVdyM7EXH+9pSWwwP9EFjK4vgp8SUg2gZHDv2D4KIqRwM1viV7EXm8Ez/NEHUAreojmRcXGZ2NO68ChK0BBpvBeIR0KzUcN6Sk88NobP0+QhMS44DQCxWpyXnNziWQiKGz8MrFBZvL0O3aY4HgUr8TKYVeBvmq0V5bbdM3Kn8AlIdgBOTRUyuOpw6zeinuK3urh13Iq7hm/kskO0B35fSAEIfhb4t+MRv4VOrZeS5tC7gr5aqrXg7frkanHu/alSKEHoQANvNslmskd3VLHxGMoOfkJv+FUZHBCeXPvOB06n4+YyV7m1tLjP/4hBJXK3uUuNUwtTHfpPUxIPZ3c74cb+BQs2vqWZ0+sB2dTizxGnzRM1bjJt1Yt";
////			byte [] bb = Encodes.decodeBase64(aa);
////			String cc= parseByte2HexStr(bb);
////			System.out.println("cccc---------"+cc);
////			String aaddc = AES256EncryptionUtils.decrypt(cc);
////			System.out.println(aaddc);
//
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		//测试邮箱加密
//		String alipay = "1231211113@qq.com";
//		String encryp_alipay = "";
//		int atFoot = alipay.indexOf("@");
//		encryp_alipay = alipay.substring(0,atFoot)+"***"+alipay.substring(atFoot);
//		System.out.println(encryp_alipay);


//		Date repaymentDay = DateUtils.parseDate(DateUtils.getYear() + "-" + DateUtils.getMonth() + "-21");
//		double diff = DateUtils.getDistanceOfTwoDate(repaymentDay, new Date());
//		System.out.println(repaymentDay);
//		System.out.println(diff);

//		System.out.println(DateUtils.formatDate(DateUtils.addDays(new Date(), -1),"yyyy-MM-dd HH:mm:ss"));

		String passWord = "openg999";
		String ePwd = SystemService.entryptPassword(passWord);
		System.out.println(ePwd);
		boolean check = SystemService.validatePassword(passWord, "bd4df7e938bff5c7347d7a746bafc6ddd795c08ca20a294f9e3433de");
		System.out.println(check);

	}
}
