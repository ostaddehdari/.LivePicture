# miss hashemi — نسخهٔ EasyAR

با باز کردن برنامه دوربین فعال می‌شود؛ وقتی دوربین روی عکس قرار بگیرد، فیلم دقیقاً روی عکس پخش می‌شود
و با موتور ردیابی **EasyAR Sense 4.11** همراه آن حرکت می‌کند. روی تقریباً همهٔ گوشی‌های اندروید (۷ به بالا) کار می‌کند
و به ARCore نیازی ندارد.

## فایل‌های مهم
| مسیر | توضیح |
|---|---|
| `app/src/main/assets/target.jpg` | عکسی که شناسایی می‌شود |
| `app/src/main/assets/video.mp4` | فیلمی که روی عکس پخش می‌شود |
| `app/src/main/java/.../ARActivity.java` | کلید لایسنس EasyAR (متغیر `KEY`) |
| `app/src/main/java/.../HelloAR.java` | عرض واقعی چاپ (`PRINT_WIDTH_METERS` = ۰٫۱۵ متر) |
| `app/libs/EasyAR.jar` + `app/src/main/jniLibs/` | کتابخانهٔ EasyAR (جایگزین EasyAR.aar که برای آپلود در GitHub بیش از ۲۵ مگابایت بود) |

## لایسنس
- کلید برای Package Name **`com.miss.hashemi`** ساخته شده است؛ اگر `applicationId` عوض شود، کلید جدید لازم است.
- نسخهٔ **Personal (رایگان)**: واترمارک EasyAR روی صفحه دیده می‌شود و **استفادهٔ تجاری مجاز نیست**.
  برای حذف واترمارک و استفادهٔ تجاری، نسخهٔ Professional یا Classic را بخرید و کلید جدید را جایگزین کنید.
- چون کلید داخل کد است، بهتر است مخزن GitHub را **Private** بسازید.

## ساخت APK
مخزن جدید در GitHub بسازید، محتویات این پوشه را آپلود کنید (پوشهٔ `.github` را فراموش نکنید)
و APK را از تب **Actions ← Artifacts** بگیرید.
این نسخه با همان کلید امضای miss hashemi قبلی امضا می‌شود و روی آن نصب می‌شود (جایگزینش می‌شود).

بر پایهٔ نمونهٔ رسمی HelloARVideo از EasyAR Sense SDK.
