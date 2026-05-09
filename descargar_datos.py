import boto3
s3 = boto3.client('s3')
# Pon tu nombre de bucket real
s3.download_file('inmobiliaria-aws-proyecto-final', 'inmuebles.dat', 'inmuebles.dat')
print("✅ Datos recuperados desde AWS con éxito.")