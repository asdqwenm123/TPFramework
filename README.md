# TPMC Framework

TPMC Framework는 마인크래프트 서버 [tpmc.kr](https://discord.gg/tpmckr)에서 사용하는 다양한 기능들을 제공하는 프레임워크입니다. 기여는 환영입니다.
(코드 읽는 건 잘하지만 쓰는 건 어렵네요 많이 도와주세요)

## 현재 포함된 기능
- **DB 관리 기능** (sqlite도 포함될 예정)
- **콘피그 관리 기능**
- **자동 리스너 등록**

## 현재 만드는 기능
- **상점 GUI**: GUI를 통해 아이템을 사고팔 수 있습니다.
- **이코노미 연동**

## 앞으로 추가될 기능 (예정)
- **귓 시스템** (폐기 가능성 O)
- **tpa 시스템** (폐기 가능성 O)

## 설치 및 사용법
**(TP서버 기준, 제 기준으로 만들어졌기 때문에, 사용법은 알아서 쓰세요.)**
1. 서버에 플러그인 설치하기
2. 가끔 켜지는 저장소에서 가져오기
```kotlin
repositories {
    maven("https://repo.sora24.kr/repository/tp-releases")
}
```

```kotlin
dependencies {
    implementation("kr.tpmc:TPFramework:버전")
}
```
3. 인스턴스 가져오기
```java
TPFramework api = Bukkit.getServicesManager().getRegistration(TPFramework.class).getProvider();
```

## 라이선스
라이센스는 GPL-3.0이며 변경 혹은 삭제를 금합니다.

---

더 많은 정보는 [tpmc.kr](https://discord.gg/tpmckr)에서 확인하세요!
