

About 
==============

다함께 던전왕을 자동 탐험해주는 메크로

- 지금은 망함

### 다함께던전왕 Wiki 
https://namu.wiki/w/%EB%8B%A4%ED%95%A8%EA%BB%98%20%EB%8D%98%EC%A0%84%EC%99%95



General
==============
### Required
>Root Permission

### Used Library
```
Google Tessart,Monkey Script
```

### Usage
`
30초 마다 Alaram Service를 활용하여 IntentService를 호출
`
#### Intent Service

##### 현재화면 스크린샷
```
Process sh = Runtime.getRuntime().exec("su", null, null);
			OutputStream os = sh.getOutputStream();
			os.write(("/system/bin/screencap -p " + "/sdcard/img.png")
					.getBytes());
			os.flush();

			os.close();
			sh.waitFor();
```

##### TessArt library를 사용하여 Image를 Test Parsing

4가지의 Text를 파싱

- 탐험완료
- 다시하기
- 보스등장
- 반지부족

##### Monkey Script를 이용하여 Device Click

```
String path = "monkey  -f  /sdcard/hero_auto/"
					+ iret + ".txt 1";

Process sh = Runtime.getRuntime().exec("su", null, null);
OutputStream os = sh.getOutputStream();
os.write((path).getBytes());
os.flush();

os.close();
sh.waitFor();
```


### ChangeLog: 

- **AUTO-DUNGEON 1.0.0**:
	- Initial Created  
	



