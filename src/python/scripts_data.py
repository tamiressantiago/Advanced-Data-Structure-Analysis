import csv
import random
import os  

random.seed(7942)

pasta_destino = os.path.join("data")

os.makedirs(pasta_destino, exist_ok=True)

def gerar_dados_sinteticos(num_elementos=5000000, minimo=1, maximo=100000000, repetidos=True, ordenado=False):
    tamanho_populacao = maximo - minimo + 1

    if not repetidos and num_elementos > tamanho_populacao:
        raise ValueError(f"num_elementos ({num_elementos}) não pode ser maior que o intervalo [{minimo}, {maximo}] ({tamanho_populacao} valores únicos disponíveis)")

    if repetidos:
        dados = [random.randint(minimo, maximo) for _ in range(num_elementos)]
    else:
        dados = random.sample(range(minimo, maximo + 1), min(num_elementos, tamanho_populacao))  

    return sorted(dados) if ordenado else dados

dados = [
    ("dados_com_repeticoes_desordenados.csv", gerar_dados_sinteticos(repetidos=True, ordenado=False)),
    ("dados_com_repeticoes_ordenados.csv", gerar_dados_sinteticos(repetidos=True, ordenado=True)),
    ("dados_sem_repeticoes_desordenados.csv", gerar_dados_sinteticos(repetidos=False, ordenado=False)),
    ("dados_sem_repeticoes_ordenados.csv", gerar_dados_sinteticos(repetidos=False, ordenado=True)),
]

def salvar_csv(nome_arquivo, valores):
    caminho_completo = os.path.join(pasta_destino, nome_arquivo)
    with open(caminho_completo, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Valor"])
        for valor in valores:
            writer.writerow([valor])
    return caminho_completo

for nome_arquivo, valores in dados:
    caminho_arquivo = salvar_csv(nome_arquivo, valores)
    print(f"Arquivo salvo em: {os.path.abspath(caminho_arquivo)}")

