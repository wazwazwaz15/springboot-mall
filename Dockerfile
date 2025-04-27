
#映像檔來源
FROM openjdk:17-jdk-slim

#維護者訊息
MAINTAINER bowei wazwazwaz15@gmail.com

#RUN 創建映像檔時執行動作


#CMD 啟動容器時執行的命令


#指定工作目錄
WORKDIR /app

#複製檔案(資料夾)    "ADD" 複製檔案(單檔)
COPY target/*.jar app.jar

#容器對外的埠號
EXPOSE 8080

#指定容器啟動後執行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]
