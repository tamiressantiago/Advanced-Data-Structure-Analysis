#Usar no colab

import csv
import random
from google.colab import files

#Alterar quantidade de dados
def gerar_dados_sinteticos(num_elementos=200000, minimo=1, maximo=1000000, repetidos=True, ordenado=False):
    if repetidos:
        dados = [random.randint(minimo, maximo) for _ in range(num_elementos)]
    else:
        dados = random.sample(range(minimo, maximo + 1), num_elementos)
    return sorted(dados) if ordenado else dados

dados = [
    ("dados_com_repeticoes_desordenados.csv", gerar_dados_sinteticos(repetidos=True, ordenado=False)),
    ("dados_com_repeticoes_ordenados.csv", gerar_dados_sinteticos(repetidos=True, ordenado=True)),
    ("dados_sem_repeticoes_desordenados.csv", gerar_dados_sinteticos(repetidos=False, ordenado=False)),
    ("dados_sem_repeticoes_ordenados.csv", gerar_dados_sinteticos(repetidos=False, ordenado=True)),
]

def salvar_csv(nome_arquivo, valores):
    with open(nome_arquivo, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Valor"]) 
        for valor in valores:
            writer.writerow([valor])

for nome_arquivo, valores in dados:
    salvar_csv(nome_arquivo, valores)
    files.download(nome_arquivo)
