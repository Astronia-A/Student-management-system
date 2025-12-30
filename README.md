这份 `README.md` 是专门为你这个“中等难度”档位的项目定制的。它不仅能清晰展示你的工作成果，还能体现你对微服务架构的理解，是 GitHub 仓库不可或缺的说明书。

你可以直接将以下内容复制并保存为项目根目录下的 **`README.md`** 文件。

---

# 学生信息管理系统 (JavaEE 课程设计 - 中等难度)

## 📝 项目简介
本项目是为 JavaEE 上机实验开发的“学生信息管理系统”。系统采用前后端分离的 **Spring Cloud 微服务架构**，严格按照实验要求完成了“中等”难度档位的所有功能，包括带验证码的安全登录、多级联动查询、数据字典管理以及基于 MinIO 的附件上传。

### 核心亮点
*   **微服务治理**：使用 Nacos 进行服务注册、发现与配置管理。
*   **统一网关**：通过 Spring Cloud Gateway 实现路由转发与跨域配置。
*   **安全验证**：登录流程集成 Redis 存储验证码，防止恶意登录。
*   **对象存储**：集成 MinIO 实现课程附件上传与在线预览。
*   **动态联动**：前端使用 Element-Plus 实现“院系-专业-班级”的异步懒加载联动。

---

## 🛠 技术栈

### 后端
*   **核心框架**：Spring Boot 2.7.18 + Spring Cloud 2021.0.8
*   **注册中心**：Spring Cloud Alibaba Nacos 2021.0.5.0
*   **网关**：Spring Cloud Gateway
*   **数据库**：MySQL 8.0 + MyBatis-Plus (3.5.3.1)
*   **缓存**：Redis (用于登录验证码校验)
*   **存储**：MinIO (课程附件/课件管理)
*   **依赖管理**：Maven 3.x

### 前端
*   **框架**：Vue 3 (Vite)
*   **UI 组件库**：Element-Plus
*   **通信**：Axios
*   **路由**：Vue Router

---

## 🚀 已实现功能 (中等难度要求)

### 1. 用户模块
*   [x] **注册功能**：支持用户名、密码及基本信息的录入。
*   [x] **安全登录**：用户名 + 密码 + 验证码。
*   [x] **验证码机制**：后端通过 `easy-captcha` 生成图片，答案加密存储于 Redis，有效期 2 分钟。

### 2. 学生信息管理 (CRUD)
*   [x] **分页查询**：支持学生列表的分页展示。
*   [x] **模糊搜索**：支持按学生姓名进行模糊匹配。
*   [x] **数据字典**：性别、政治面貌等字段通过数据库 `sys_dict` 表动态加载。
*   [x] **班级联动**：实现“院系 -> 专业 -> 班级”的多级异步联动选择（Lazy Load）。

### 3. 课程管理任务
*   [x] **附件上传**：支持将 Word/PDF/PPT 课件上传至 MinIO 对象存储服务器。
*   [x] **附件预览**：上传成功后自动生成预览链接，点击即可跳转查看。

---

## 📦 项目结构

```text
Student-Management-System
├── student-system-parent      # 后端父工程
│   ├── common                 # 公共模块（通用返回类、工具类）
│   ├── gateway                # 统一网关模块 (Port: 8080)
│   ├── service-student        # 业务逻辑模块 (Port: 8081)
├── student-system-frontend    # 前端工程 (Vue 3 + Element-Plus)
└── sql                        # 数据库初始化脚本
```

---

## ⚙️ 快速启动

### 1. 环境准备
*   启动 **MySQL** (创建数据库 `student_system` 并运行 `sql/init.sql`)
*   启动 **Nacos** (以单机模式 `startup.cmd -m standalone` 启动)
*   启动 **Redis**
*   启动 **MinIO** (创建名为 `course` 的 Bucket，并将访问策略设为 `Public`)

### 2. 后端启动
1.  修改 `service-student/src/main/resources/application.yml` 中的数据库密码。
2.  分别启动 `GatewayApplication` 和 `StudentApplication`。

### 3. 前端启动
```bash
cd student-system-frontend
npm install
npm run dev
```
访问 `http://localhost:5173` 即可进入系统。

---

## 📸 界面预览
*(此处你可以根据实际运行情况截图并替换)*
*   **登录页**：包含算术/字符验证码。
*   **管理页**：包含搜索栏、学生表格、分页组件及操作按钮。

---
**提示：** 本任务仅为 JavaEE 上机实验内容，非最终考核任务。
