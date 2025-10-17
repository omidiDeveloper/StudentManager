# 🎓 Student Manager Application

A full-stack **Student Management System** built with **Kotlin (Android)** and **Spring Boot (Backend)**.  
This project allows users to **add, view, update, and delete student records** easily with a clean UI and modern architecture.

---

## 🧠 Overview

This project demonstrates a **complete Android–Spring Boot integration** using:
- 🧩 **MVVM architecture** on Android  
- ⚡ **RxJava2** for asynchronous data handling  
- 🌐 **Retrofit** for API communication  
- 💾 **Spring Boot REST API** as the backend  

> 💡 You can view the **Spring Boot backend project** in my other repository:  
> 🔗 [Student Manager Backend Repository](https://github.com/omidiDeveloper/crudSpringBoot)

---

## 🚀 Features

### 🖥️ Android App (Frontend)
- Developed with **Kotlin**
- **MVVM architecture pattern**
- **Retrofit + RxJava2** integration
- **SweetAlertDialog** for modern alerts
- **ViewBinding** enabled
- CRUD operations: Add, View, Update, Delete students

### 🗄️ Spring Boot Backend
- **RESTful API** using Spring Boot  
- **MVC architecture**
- **JPA Repository** for database operations  
- Connected to **MySQL / H2**  
- Endpoints:
  - `POST /students` → Add new student  
  - `GET /students` → Retrieve all students  
  - `PUT /students/{id}` → Update student  
  - `DELETE /students/{id}` → Delete student  

---

## 🛠️ Tech Stack

### Android
| Component | Technology |
|------------|-------------|
| Language | Kotlin |
| Architecture | MVVM |
| Networking | Retrofit2 + RxJava2 |
| UI | ViewBinding, SweetAlertDialog |
| Min SDK | 24 |
| Target SDK | 36 |

### Backend
| Component | Technology |
|------------|-------------|
| Framework | Spring Boot |
| Architecture | MVC |
| Language | Kotlin / Java |
| Database | MySQL / H2 |
| Architecture | MVC |
| Build Tool | Gradle / Maven |

---

## 📂 Project Structure (Android)

```

com.example.studentmanagermvcandrxjava/
├── model/
|  ├──ApiService
|  ├──Student
|  └──MainRepository
|
├── MainScreen/
|   ├──MainScreenActivity
|   ├──MainScreenViewModel
|   └──StudentAdapter
|
├── AddStudent/
|   ├──AddStudentActivity
|   └──AddStudentViewModel
|
└── utils/
|   ├──Extentions
|   ├──Utils
|   └──Constance
|
````

---

## ⚙️ Installation & Setup

### 🔧 Backend Setup
You can find the backend setup instructions in the backend repository:  
👉 [Student Manager Backend Repository](https://github.com/omidiDeveloper/crudSpringBoot)

### 📱 Android Setup
```bash
git clone https://github.com/omidiDeveloper/StudentManager
cd StudentManager
````

In Retrofit config:

```kotlin
const val BASE_URL = "http://YOUR_LOCAL_IP:8080/"
```

Then build and run on Android Studio.

---

## 📸 Screenshots (Optional)

<img width="439" height="980" alt="image" src="https://github.com/user-attachments/assets/5f94723c-e83f-4bcb-a197-bfeb3d6fba17" />  <img width="449" height="939" alt="image" src="https://github.com/user-attachments/assets/5863ba3b-81f3-49f3-9500-6d56a05114df" />  <img width="458" height="928" alt="image" src="https://github.com/user-attachments/assets/c74e45fa-4163-462a-882f-021ab8247bab" />  <img width="443" height="955" alt="image" src="https://github.com/user-attachments/assets/f3adbdc6-122a-4657-9e43-76b713b31027" />





---

## 🧪 Example API Response

```json
[
  {
    "id": 1,
    "name": "Mohammad Omidi",
    "course": "Android",
    "score": "20"
  },
  {
    "id": 2,
    "name": "test user",
    "course": "Physics",
    "score": "10"
  }
]
```

---

## 📦 Dependencies (Android)

```gradle
implementation "com.squareup.retrofit2:retrofit:3.0.0"
implementation "com.squareup.retrofit2:converter-gson:3.0.0"
implementation "com.squareup.retrofit2:adapter-rxjava2:3.0.0"
implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
implementation "io.reactivex.rxjava2:rxjava:2.2.21"
implementation "com.github.f0ris.sweetalert:library:1.6.2"
```

---

## 👨‍💻 Author

**Mohammad Omidi**

📍 Android Developer | Kotlin & Spring Boot Enthusiast

-📧 [OmidiKotlin@gmail.com](mailto:OmidiKotlin@gmail.com) </br>
-💻 [GitHub: @omidiDeveloper](https://github.com/omidiDeveloper) </br>
-🔗 [LinkedIn: Mohammad Omidi Zadeh](https://www.linkedin.com/in/mohammad-omidi-zadeh-948740263) </br>

---

## 📝 License

```
MIT License © 2025 Mohammad Omidi
```

⭐ **If you like this project, give it a star!**

```
```
