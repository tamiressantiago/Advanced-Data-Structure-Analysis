import csv
import random
from google.colab import files

# Função para gerar dados sintéticos
def gerar_dados_sinteticos(num_elementos=200000, minimo=1, maximo=1000000, repetidos=True, ordenado=False):
    if repetidos:
        dados = [random.randint(minimo, maximo) for _ in range(num_elementos)]
    else:
        dados = random.sample(range(minimo, maximo + 1), num_elementos)
    return sorted(dados) if ordenado else dados

# Gerar conjuntos de dados
dados = [
    ("dados_com_repeticoes_desordenados.csv", "Sim", "Desordenado", gerar_dados_sinteticos(repetidos=True, ordenado=False)),
    ("dados_com_repeticoes_ordenados.csv", "Sim", "Ordenado", gerar_dados_sinteticos(repetidos=True, ordenado=True)),
    ("dados_sem_repeticoes_desordenados.csv", "Não", "Desordenado", gerar_dados_sinteticos(repetidos=False, ordenado=False)),
    ("dados_sem_repeticoes_ordenados.csv", "Não", "Ordenado", gerar_dados_sinteticos(repetidos=False, ordenado=True)),
]

# Salvar os dados em arquivos CSV
def salvar_csv(nome_arquivo, repeticao, ordenacao, valores):
    with open(nome_arquivo, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Repetição", "Ordenação", "Valor"])  # Cabeçalhos
        for valor in valores:
            writer.writerow([repeticao, ordenacao, valor])

# Gerar e salvar os arquivos
for nome_arquivo, repeticao, ordenacao, valores in dados:
    salvar_csv(nome_arquivo, repeticao, ordenacao, valores)
    files.download(nome_arquivo)  # Permitir o download no Google Colab
