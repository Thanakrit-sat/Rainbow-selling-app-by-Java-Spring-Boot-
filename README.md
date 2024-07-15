# cs211-641-project

## รายชื่อสมาชิก
* (6310450514) (ธนกฤต ศรัทธาบุญ) (Thanakrit-sat)
  * view ของ login
  * models ของ login
  * controller ของ login
* (6310450549) (ธีรภัทร์ กุยยาวัฒนานนท์) (Teerapat-Kuiyawattananon)
  * view ของ register
  * models ของ register
  * controller ของ register
* (6310451367) (รวิภาส ดำเด่นงาม) (Rawipas-Damdenngam)
  * view ของ dev
  * models ของ dev
  * controller ของ dev
* (6310451448) (เอธัส สกุลพิทยาธร) (Athat1448)
  * view ของ market
  * models ของ market
  * controller ของ market


## วิธีการติดตั้งหรือรันโปรแกรม
* download program จาก https://github.com/CS211-641/project-641-becomemonke เป็นไฟล์ ZIP
  * จากนั้น ให้แตกไฟล์ จะได้โฟล์เดอร์ project-641-becomemonke-main ให้เข้าไปใน directory ที่ /project-641-becomemonke-main/project-641-becomemonke-main/cs211-431-project
  * จากนั้น run command: ./bin/launch.sh เพื่อ run program

## การวางโครงสร้างไฟล์
* การวางโครงสร้างไฟล์นั้นแบ่งออกเป็น 3 ส่วน
  * csv-database
    * มีหน้าที่เก็บค่าต่าง ๆ ของ user และสินค้า โดยแบ่งเป็น 5 ไฟล์
    * 1.comment-list.csv มีหน้าที่เก็บข้อมูลของคนที่ comment ไว้รวมไปถึงข้อความและคะแนนความถึงพอใจ
    * 2.order-list.csv มีหน้าที่เก็บข้อมูลรายการสั่งซื้อ
    * 3.products.csv มีหน้าที่เก็บข้อมูลของสินค้าต่าง ๆ ภายในร้านค้า
    * 4.report-list.csv มีหน้าที่ในการเก็บข้อมูลต่าง ๆ ของการรีพอร์ทสินค้า
    * 5.useraccount.csv มีหน้าที่เก็บข้อมูลของ user ทุกคนภายในระบบ
  * ไฟล์การทำงาน โดยจะแสดงใน directory: project-641-becomemonke\src\main\java\ku\cs โดยจะแบ่งเป็น controllers, models, services
    * controllers
      * เก็บ controller ต่าง ๆ ของหน้า UI เพื่อกำหนดการทำงานของหน้านั้น ๆ
    * models
      * เก็บไฟล์ model เพื่อคิดคำนวณการทำงานของ program
    * services
      * เก็บไฟล์ที่มีหน้าที่ในการอ่านเขียนไฟล์ รวมไปถึงการ interface ที่ใช้งานภายใน program
    * App เป็น main program ที่ใช้ run program ทั้งระบบ
  * ไฟล์การแสดงผล โดยจะอยู่ใน directory project-641-becomemonke\src\main\resources จะเก็บไฟล์อยู่ 2 แบบคือ
    * images
      * เก็บรูปที่ใช้ทั้ง program
    * fxml
      * ไฟล์ fmxl ต่าง ๆ มีหน้าที่ในการเก็บ UI ของหน้าต่าง ๆ ภายในระบบ

## ตัวอย่างข้อมูลผู้ใช้ระบบ
* (admin) (admin) (1234)
* (admin) (teerapat) (0549)
* (member) (test01) (0001)
* (member) (Kawaii) (Kawaiidesu)
* (member) (Bew) (3699) (เป็นตัวอย่างโดนระงับการใช้งานซึ่งสามารถเปิดปิดได้หากมี role เป็น admin)

## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ
* ครั้งที่ 1 (13/08/2021)
  * ทำ UI ของหน้า LOGIN ให้สามารถใช้งานได้ โดยมีการบอกว่าการใส่รหัสหรือ username ผิดหรือไม่ ถ้าผิดจะไม่สาารถเข้าไปหน้า market ได้และขึ้นแจ้งเตือน
  * ตกแต่งหน้าตา UI ให้มีความน่าใช้
    * (พัฒนาโดยใคร: Thanakrit-sat)
  * ทำ UI ของหน้า Register และ  RegisterController
  * ทำ model ของ Register ให้มีการตรวจสอบข้อมูลที่ใส่เข้ามา ใส่ข้อมูลครบไหม พาสเวิร์ดตรงกันไหม ชื่อผู้ใช้ซ้ำไหมแต่ยังไม่สามารถบันทึกข้อมูลได้
  * ปุ่มย้อนกลับไปหน้า Login
  * ทำ model ของ MemberAccount ที่เป็น class ของผู้ใช้
    * (พัฒนาโดย: Teerapat-Kuiyawattananon)
  * ทำ menu ของร้านค้า
    * (พัฒนาโดยใคร: Athat1448)
  * ทำ model ของ Developer
    * (พัฒนาโดยใคร: Rawipas-Damdenngam)
* ครั้งที่ 2 (10/09/2021)
  * ทำหน้า login ให้สามารถอ่านไฟล์ได้จาก useraccount.csv
  * ทำ product model
    * (พัฒนาโดยใคร: Thanakrit-sat )
  * ทำหน้า login & register ให้สามารถอ่านและเขียนไฟล์ได้จาก useraccount.csv
    * (พัฒนาโดยใคร: Teerapat-Kuiyawattananon)
  * เพืมหน้า listview ในร้านค้า
    * (พัฒนาโดยใคร: Athat1448)
  * ทำ UI ของร้านค้า
  * ทำ Suggesstion controller
    * (พัฒนาโดยใคร: Rawipas-Damdenngam)
* ครั้งที่ 3 (30/09/2021)
  * ทำให้สามารถอ่านเขียนไฟล์ในไฟล์ csv ของ memberAccount ได้ และทำ UI ในหน้า profile
  * ทำ UI หน้า market และทำ GridPane ของสินค้าในหน้า market ให้สามารถอ่านค่าในไฟล์ products.csv มาใช้แสดงผลได้
    * (พัฒนาโดยใคร: Thanakrit-sat)
  * ทำหน้า profile ให้สามารถกดเข้าไปได้ และสามารถแก้ไข password ได้ พร้อมแสดงข้อมูลต่าง ๆของ user นั้นๆ
    * (พัฒนาโดยใคร: Teerapat-Kuiyawattananon)
  * ทำ menu ต่าง ๆ ในหน้า market ให้สามารถใช้งานได้ รวมถึงปรับแก้ UI ในหน้า market
    * (พัฒนาโดยใคร: Athat1448)
  * ทำแก้ UI market รวมถึง fix bug ต่าง ๆ ที่เกิดในหน้า market
    * (พัฒนาโดยใคร: Rawipas-Damdenngam)  