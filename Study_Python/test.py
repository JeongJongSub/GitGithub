import requests
import math

location = "경기 김포시 김포대로 지하 710 (사우동)"
url = f"https://dapi.kakao.com/v2/local/search/address.json?query={location}"
headers = {
    "Authorization" : f"KakaoAK 6da2d4ef4e4d4ac3f024dba116cd0233"
}
result = requests.get(url, headers = headers)
json_obj = result.json()
#print(json_obj)
latitude = float(json_obj['documents'][0]['y'])
longitude = float(json_obj['documents'][0]['x'])

def get_url(latitude, longitude, page):
    return f"https://dapi.kakao.com/v2/local/search/category.json?category_group_code=HP8&radius=20000&x={longitude}&y={latitude}&size=10&page={page}"

def haversine(lat1, lon1, lat2, lon2):
    R = 6371000

    lat1, lon1, lat2, lon2 = map(math.radians, [lat1, lon1, lat2, lon2])

    dlat = lat2 - lat1
    dlon = lon2 - lon1

    a = math.sin(dlat / 2) ** 2 + math.cos(lat1) * math.cos(lat2) * math.sin(dlon / 2) ** 2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))

    distance = R * c
    return distance

def get_hospitals(latitude, longitude, num_pages = 5) :
    all_hospitals = []

    for page in range(1, num_pages + 1) :
        all_url = get_url(latitude, longitude, page)
        response = requests.get(all_url, headers = headers)

        if response.status_code == 200:
            data = response.json()
            hospitals = data.get('documents', [])
            all_hospitals.extend(hospitals)

        else:
            print(f"Error: {response.status_code}")
            break

    return all_hospitals

find_hospitals = get_hospitals(latitude, longitude, num_pages = 5)

for idx, hospital in enumerate(find_hospitals, 1):
    place_name = hospital['place_name']
    address = hospital['address_name']

    hospital_lat = float(hospital['y'])
    hospital_lon = float(hospital['x'])

    distance = haversine(latitude, longitude, hospital_lat, hospital_lon)

    print(f"{idx}. {place_name} - {address} - 거리: {round(distance, 2)}m")