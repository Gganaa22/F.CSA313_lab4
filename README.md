# F.CSA313 — Лаборатори №4: JUnit 5 нэгжийн тест

- **Нэр:** Д.Гантогтох
- **Код:** B242270139

## Орчин

```
java version "25.0.2" 2026-01-20 LTS
Java(TM) SE Runtime Environment (build 25.0.2+10-LTS-69)
Java HotSpot(TM) 64-Bit Server VM (build 25.0.2+10-LTS-69, mixed mode, sharing)
```

```
Apache Maven 3.9.12 (848fbb4bf2d427b72bdb2471c22fced7ebd9a7a1)
Maven home: C:\Program Files\apache-maven-3.9.12
Java version: 25.0.2, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-25.0.2
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Төслийг `maven.compiler.release=17` тохиргоотойгоор Java 25 дээр ажиллуулсан.

## Үр дүн

- Тестийн методын тоо: 11 (8 `@Test`, 3 `@ParameterizedTest`)
- `results/mvn-test.txt`-ийн `Tests run`: **33**, Failures: 0, Errors: 0, Skipped: 0, BUILD SUCCESS
- Мутаци (`score >= 90` → `score > 90`): `results/mvn-test-mutant.txt`-д 33 тестээс 2 унасан, BUILD FAILURE. Унасан тестүүд: `ninetyIsExactlyA` (`expected: <A> but was: <B>`) болон `letterGradeParameterized[2]` (90,A мөр). Дараа нь `>= 90` болгож буцаагаад дахин ногоон болгосон.

## Дүгнэлт

Энэ лабораторийн ажилд GradeCalculator классыг бичиж, JUnit 5-аар 11 тестийн метод бичсэн бөгөөд Surefire CsvSource-ийн мөр бүрийг тусад нь тоолсон тул нийт 33 тест ажилласан. Ердийн утгууд (95, 85, 75 гэх мэт), хязгаарын утгууд (90, 89.99, 60, 59.99, 0, 100) болон буруу оролтын exception-ийг Arrange–Act–Assert бүтэцтэй шалгасан. Сонирхолтой зүйл нь мутацийн туршилт байлаа: `score >= 90`-ийг `score > 90` болгоход `ninetyIsExactlyA` болон parameterized-ийн `90,A` мөр унасан. Харин 95→A шиг ердийн утгын тест мутацийг илрүүлж чадахгүй байсан тул `>=` ба `>`-ийн ялгааг зөвхөн яг хязгаар дээрх тест барьдаг гэдгийг ойлгосон. Энэ нь "тест pass болсон нь тест зөв гэсэн үг биш" гэсэн сургамжийг бодитоор харууллаа. Хоёр дахь сонирхолтой алдаа бол `NaN` оролт байв: `NaN < 0` ба `NaN > 100` хоёул false тул анхны хувилбарт `letterGrade(Double.NaN)` exception шидэхгүй "F" буцаах байсан. Үүнийг `Double.isNaN` шалгалтаар засаж, `nanInputThrows` тест бичсэн. Мөн `totalScore`-ийн exception-ийг зөвхөн `att`, `lab` дээр биш, таван талбар бүрийн сөрөг ба дээд хязгаараас хэтэрсэн утгаар parameterized тестээр шалгасан. Ажлын явцад `git add` нь `.metals/` хавтасны түгжигдсэн файлаас болж алдаа өгсөн тул үүнийг `.gitignore`-д нэмж шийдсэн.