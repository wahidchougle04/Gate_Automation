# IoT-Based Entrance Gate Automation System

An automated access control and gate operation system leveraging IoT microcontrollers, real-time sensor integration, and computer vision (ANPR) for secure residential and commercial access management.

---

## Overview

Traditional entrance gates relying on manual operation or standard locks lack remote monitoring, real-time access logging, and automated threat notifications. This project delivers an automated access control infrastructure built around an IoT microcontroller ecosystem. 

The system processes real-time sensor telemetry, validates vehicle license plates against a central database, executes motorized gate mechanisms, and streams live access logs to a web interface.

---

## Key Features

* **Automatic Number Plate Recognition (ANPR):** Real-time image capture and license plate extraction for authorized entry validation.
* **Automated Actuation:** Servo/motor-driven barrier control triggered automatically upon access verification.
* **Proximity & Obstacle Detection:** Integrated ultrasonic/IR sensor network for vehicle detection and closing-gate safety.
* **Access Logging & Telemetry:** Real-time entry/exit timestamping, vehicle identification, and image path storage in a relational database.
* **Multi-Role Access Control:** Admin and Security role separation for credential management, temporary access passes, and system logs.
* **Alert System:** Instant notifications triggered on unauthorized entry attempts or anomaly detection.

---

## Tech Stack

* **Hardware Core:** Raspberry Pi / ESP32 / Arduino
* **Sensing & Actuation:** IR / Ultrasonic Sensors, Servo / Stepper Motors, Relay Modules, Camera Module
* **Backend:** Java Servlets (Apache Tomcat), Python (OpenCV / Data Processing)
* **Frontend:** HTML5, CSS3, JavaScript, Bootstrap
* **Database:** MongoDB / MySQL (Relational schema for vehicle records, access logs, user roles, and temporary access)
* **Protocols:** HTTP, RESTful APIs, MQTT / WebSockets

---

## Database Architecture

The data architecture enforces operational integrity across four primary relational schemas:

| Schema | Key Fields | Purpose |
| :--- | :--- | :--- |
| **`vehicles`** | `vehicle_id` (PK), `owner_name`, `owner_contact`, `status` | Stores registered vehicle authorization details. |
| **`access_logs`** | `log_id` (PK), `vehicle_id` (FK), `entry_time`, `exit_time`, `recognized`, `image_path` | Tracks real-time entry/exit events and verification images. |
| **`users`** | `user_id` (PK), `username`, `password_hash`, `role` | Manages system administrative and security personnel roles. |
| **`temporary_access`**| `temp_id` (PK), `vehicle_id` (FK), `valid_from`, `valid_until`, `approved_by` | Manages temporary access windows for guests and delivery vehicles. |

---

## System Workflow

```text
  [ Vehicle Approaches ]
            │
            ▼
[ Ultrasonic / IR Sensor ] ─── (Detects Motion) ───► [ Camera Module ]
                                                            │
                                                            ▼
                                                  [ Process License Plate ]
                                                            │
                                                            ▼
[ Gate Actuator (Open) ] ◄─── (Access Granted) ─── [ Validate via Database ]
           │                                                │
           │                                                ▼
           └────────────────► [ Log Timestamp & Image ] ◄── (Access Denied / Alert)
