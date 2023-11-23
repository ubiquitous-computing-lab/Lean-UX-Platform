import random
import json

def generate_user_task_data(num_users, num_tasks):

    user_task_data = {
        f"user {user_id}": {
            f"task {task_id}": {"Task_success": bool(random.getrandbits(1))}
            for task_id in range(1, num_tasks)
        }
        for user_id in range(1, num_users)
    }

    # Convert to JSON
    return  json.dumps(user_task_data, indent=4)



