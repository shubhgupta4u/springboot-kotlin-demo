# Deploy and Run

## On Linux
Copy the JAR File to the Server:

``` bash
scp target/springboot-bgtask-demo-0.0.1-SNAPSHOT.jar user@your-vm:/path/to/app.jar
``` 

### Create a Systemd Service:
``` bash
sudo nano /etc/systemd/system/scheduler-task.service
``` 

### Add the Following Content:

ini
``` bash
[Unit]
Description=Spring Scheduler Task
After=network.target

[Service]
ExecStart=/usr/bin/java -jar /path/to/app.jar
Restart=always
User=youruser
Group=yourgroup

[Install]
WantedBy=multi-user.target
Enable and Start the Service:
``` 

``` bash
sudo systemctl daemon-reload
sudo systemctl enable scheduler-task
sudo systemctl start scheduler-task
``` 

### Check Logs:
``` bash
sudo journalctl -u scheduler-task -f
``` 

## On Windows
Install NSSM: Download NSSM (Non-Sucking Service Manager) and install it.

### Add the JAR File as a Service: Open Command Prompt as Administrator and run:

``` bash
nssm install SpringBootBgTaskDemo "C:\Program Files\Java\jdk-22\bin\java.exe" -jar E:\Kotlin\springboot-bgtask-demo\target\springboot-bgtask-demo-0.0.1-SNAPSHOT.jar
``` 

### Start the Service:

``` bash
nssm start SpringBootBgTaskDemo
``` 
