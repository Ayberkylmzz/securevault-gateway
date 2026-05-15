# SecureVault Gateway

SecureVault Gateway, mikroservis mimarileri için tasarlanmış, Spring Cloud Gateway tabanlı yüksek güvenlikli bir sınır kontrol ünitesidir. Proje; dinamik yönlendirme, hız sınırlama ve merkezi güvenlik denetimi sağlamak amacıyla geliştirilmiştir.

## 🚀 Temel Özellikler

* **API Gateway Routing:** Tüm trafik tek bir merkezden yönetilir ve ilgili mikroservislere güvenli bir şekilde yönlendirilir.
* **Rate Limiting:** Redis tabanlı "Token Bucket" algoritması ile IP başına anlık istek sınırı (DDoS koruması).
* **Security Filtering:** Özel API Key kontrolü ve JWT doğrulama altyapısı.
* **Reactive Logging:** Global filtreler ile tüm isteklerin (Request/Response) asenkron olarak izlenmesi ve loglanması.
* **Resilience:** Resilience4j entegrasyonu ile Circuit Breaker ve Fallback mekanizmaları.

## 🛠️ Teknoloji Yığını

* **Framework:** Spring Boot 3.4.1 / Spring Cloud Gateway
* **Runtime:** Java 21 (Temurin)
* **Data Store:** Redis (Rate Limiting için)
* **Infrastructure:** Docker & Docker Compose
* **Messaging:** Apache Kafka (Audit log entegrasyonu için hazır)
* **Security:** Spring Security & JJWT

## 📦 Kurulum ve Çalıştırma

1.  **Projeyi Klonlayın:**
    ```bash
    git clone [https://github.com/kullanici-adin/securevault-gateway.git](https://github.com/kullanici-adin/securevault-gateway.git)
    cd securevault-gateway
    ```

2.  **Ortam Değişkenlerini Ayarlayın:**
    Kök dizinde bir `.env` dosyası oluşturun ve aşağıdaki değişkenleri tanımlayın:
    ```env
    SERVER_PORT=8080
    SECURE_VAULT_API_KEY=your_api_key
    JWT_SECRET=your_32_character_secret_key
    ```

3.  **Docker ile Başlatın:**
    ```bash
    docker-compose up --build
    ```

## 🛡️ Güvenlik ve İzleme

Proje, gelen her isteği `RequestLoggingFilter` üzerinden geçirerek zaman damgası, metod ve hedef yol bilgilerini loglar. Hatalı istekler `GlobalExceptionHandler` tarafından yakalanarak kullanıcıya güvenli hata mesajları döner.

---
Developed by Ayberk Yılmaz