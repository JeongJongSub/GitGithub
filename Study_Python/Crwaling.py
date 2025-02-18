import requests
print(dir(requests))

# Requests 사용하기
res = requests.get(url='https://www.daum.net',headers={'User-Agent':'Mozilla/5.0'})