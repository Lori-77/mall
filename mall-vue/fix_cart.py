with open('src/views/Cart.vue', 'r', encoding='utf-8') as f:
    lines = f.readlines()

new_lines = []
skip = False
for line in lines:
    if '// 下单' in line:
        skip = True
        continue
    if skip:
        if line.strip() == '' or line.strip().startswith('//'):
            continue
        skip = False
    if '@click="submitOrder"' in line:
        line = line.replace('@click="submitOrder"', '@click="router.push(\'/order-confirm\')"')
    new_lines.append(line)

with open('src/views/Cart.vue', 'w', encoding='utf-8') as f:
    f.writelines(new_lines)

print('done')
