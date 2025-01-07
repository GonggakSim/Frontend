![400186909-81bbb2fa-eafd-4689-b84e-42a9b819c13a](https://github.com/user-attachments/assets/7ebc6401-8d66-43d3-8d19-63c7d56a81a3)

# Front-end 팀원

1. 이민서
2. 박찬
3. 예나경
4. 장준영

---

## branch 전략

main branch와 하위 각 팀원별 branch 이용
- main branch: 배포 직전 단계의 브랜치. develop branch에서 개발이 끝나면 사용
- develop branch : main branch의 하위 브랜치로써, 개발 프로세스를 진행하는 브랜치
- 개인 branch : develop branch의 하위 브랜치로, 팀원 개개인이 담당한 기능을 개발하는 브랜치

---

## issue 전략

태그로 구분하며 두가지 종류의 이슈를 다룸
- [Request]: 개선이 필요한 사항에 대한 요청 이슈
- [Error]: 오류가 있는 경우에 대한 이슈

---

## Pull request 전략

태그로 구분하며 개발 및 기능 추가를 다룸
- [Develop]: 개발 완료에 대한 태그
- [Add]: 개선사항에 따른 기능 추가 태그

---

## commit 컨벤션

각 태그를 이용하여 어떤 내용이 변경되었는지를 나타내는 규칙
- [Feat]: 새로운 기능 추가
- [Fix]: 버그 수정
- [Docs]: 문서 수정
- [Design]: UI 수정
- [Rename]: 파일명, 폴더명 변경
- [Remove]: 파일 삭제

---

## 코드 컨벤션

코드 작성하면서 이름을 지정해야 하는 것들에 대한 규칙
- PascalCase(파스칼 케이스): 함수, 클래스
- camelCase(카멜 케이스): 변수
- UPPER_CASE(어퍼 케이스): 상수

---

## 안드로이드 스튜디오 세팅

- 버전: Ladybug(2024.2.1)
- targetSDK, minSDK: 34로 설정
- 테스트: IDE 내 Emulator로 테스트
