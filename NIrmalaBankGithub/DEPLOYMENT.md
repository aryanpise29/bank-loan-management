# 🚀 Deployment Guide - Bank Loan Management System

## Local Deployment (Development)

### Prerequisites
1. Java 25
2. MySQL 8.0 or higher
3. Maven 3.6+

### Steps

#### 1. Setup MySQL Database
```sql
CREATE DATABASE bank_loan_db;
```

#### 2. Configure Application
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_loan_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

#### 3. Build Application
```bash
mvn clean install
```

#### 4. Run Application
```bash
mvn spring-boot:run
```

#### 5. Access Application
```
http://localhost:8080
Username: admin
Password: admin123
```

---

## Production Deployment (Linux Server)

### Prerequisites
- Linux server (Ubuntu/CentOS)
- Java 25 installed
- MySQL 8 installed
- Minimum 2GB RAM

### Step 1: Install Java
```bash
sudo apt update
sudo apt install openjdk-25-jdk -y
java -version
```

### Step 2: Install MySQL
```bash
sudo apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql
```

### Step 3: Create Database
```bash
sudo mysql -u root -p
```
```sql
CREATE DATABASE bank_loan_db;
CREATE USER 'loanapp'@'localhost' IDENTIFIED BY 'SecurePassword123!';
GRANT ALL PRIVILEGES ON bank_loan_db.* TO 'loanapp'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

### Step 4: Create Application User
```bash
sudo useradd -m -s /bin/bash loanapp
sudo mkdir -p /opt/bankloan
sudo chown loanapp:loanapp /opt/bankloan
```

### Step 5: Build and Deploy Application
```bash
# On your local machine
mvn clean package

# Copy JAR to server
scp target/loan-management-system-0.0.1-SNAPSHOT.jar user@server:/opt/bankloan/

# On server
sudo chown loanapp:loanapp /opt/bankloan/loan-management-system-0.0.1-SNAPSHOT.jar
```

### Step 6: Create Production Configuration
```bash
sudo nano /opt/bankloan/application-prod.properties
```

Add:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_loan_db
spring.datasource.username=loanapp
spring.datasource.password=SecurePassword123!
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.root=WARN
logging.level.com.bank.loan=INFO
server.port=8080
```

### Step 7: Create Systemd Service
```bash
sudo nano /etc/systemd/system/bankloan.service
```

Add:
```ini
[Unit]
Description=Bank Loan Management System
After=mysql.service
Requires=mysql.service

[Service]
Type=simple
User=loanapp
WorkingDirectory=/opt/bankloan
ExecStart=/usr/bin/java -jar /opt/bankloan/loan-management-system-0.0.1-SNAPSHOT.jar --spring.config.location=/opt/bankloan/application-prod.properties
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal
SyslogIdentifier=bankloan

[Install]
WantedBy=multi-user.target
```

### Step 8: Start Service
```bash
sudo systemctl daemon-reload
sudo systemctl enable bankloan
sudo systemctl start bankloan
sudo systemctl status bankloan
```

### Step 9: Configure Firewall
```bash
sudo ufw allow 8080/tcp
sudo ufw reload
```

### Step 10: Setup Nginx Reverse Proxy (Optional)
```bash
sudo apt install nginx -y
sudo nano /etc/nginx/sites-available/bankloan
```

Add:
```nginx
server {
    listen 80;
    server_name your-domain.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

```bash
sudo ln -s /etc/nginx/sites-available/bankloan /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

---

## Production Deployment (Windows Server)

### Step 1: Install Java
Download and install Java 25 from Oracle or OpenJDK

### Step 2: Install MySQL
Download and install MySQL 8 from official website

### Step 3: Create Database
```sql
CREATE DATABASE bank_loan_db;
```

### Step 4: Deploy Application
1. Copy JAR file to `C:\BankLoan\`
2. Create `application-prod.properties` in same folder
3. Create batch file `start-bankloan.bat`:

```batch
@echo off
java -jar C:\BankLoan\loan-management-system-0.0.1-SNAPSHOT.jar --spring.config.location=C:\BankLoan\application-prod.properties
```

### Step 5: Create Windows Service
Use NSSM (Non-Sucking Service Manager):
```cmd
nssm install BankLoan "C:\Program Files\Java\jdk-25\bin\java.exe" "-jar C:\BankLoan\loan-management-system-0.0.1-SNAPSHOT.jar --spring.config.location=C:\BankLoan\application-prod.properties"
nssm start BankLoan
```

---

## Monitoring & Maintenance

### Check Application Status
```bash
sudo systemctl status bankloan
```

### View Logs
```bash
sudo journalctl -u bankloan -f
```

### Restart Application
```bash
sudo systemctl restart bankloan
```

### Stop Application
```bash
sudo systemctl stop bankloan
```

### Database Backup
```bash
mysqldump -u loanapp -p bank_loan_db > backup_$(date +%Y%m%d).sql
```

### Database Restore
```bash
mysql -u loanapp -p bank_loan_db < backup_20240101.sql
```

---

## Performance Tuning

### JVM Options
Edit service file and add:
```
ExecStart=/usr/bin/java -Xms512m -Xmx2g -jar ...
```

### MySQL Optimization
Edit `/etc/mysql/mysql.conf.d/mysqld.cnf`:
```ini
max_connections = 200
innodb_buffer_pool_size = 1G
```

---

## Security Checklist

- [ ] Change default admin password
- [ ] Use strong database password
- [ ] Enable firewall
- [ ] Use HTTPS (SSL certificate)
- [ ] Regular security updates
- [ ] Database backups
- [ ] Monitor logs
- [ ] Restrict database access

---

## Troubleshooting

### Application won't start
```bash
# Check logs
sudo journalctl -u bankloan -n 100

# Check if port is in use
sudo netstat -tulpn | grep 8080

# Check MySQL connection
mysql -u loanapp -p bank_loan_db
```

### High memory usage
```bash
# Check Java process
ps aux | grep java

# Adjust JVM memory
# Edit service file: -Xmx1g
```

### Database connection errors
```bash
# Check MySQL status
sudo systemctl status mysql

# Check database exists
mysql -u root -p -e "SHOW DATABASES;"
```

---

## Scaling Considerations

### Horizontal Scaling
- Use load balancer (Nginx/HAProxy)
- Deploy multiple instances
- Shared database

### Vertical Scaling
- Increase server resources
- Optimize JVM heap size
- Database connection pooling

---

## Support & Maintenance

### Regular Tasks
- Daily: Check logs
- Weekly: Database backup
- Monthly: Security updates
- Quarterly: Performance review

### Update Procedure
1. Backup database
2. Stop application
3. Deploy new JAR
4. Start application
5. Verify functionality

---

**Deployment Status:** Production Ready ✅  
**Last Updated:** 2024  
**Support:** Contact development team for issues
