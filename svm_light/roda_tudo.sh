#!/bin/sh


echo "treinando Areia..."
./svm_learn AREIA.dat model_AREIA.dat

echo "Classificando areia..."
./svm_classify IMAGEM.dat model_AREIA.dat result_AREIA.dat

echo "treinando Ceu..."
./svm_learn CEU.dat model_CEU.dat

echo "Classificando Ceu..."
./svm_classify IMAGEM.dat model_CEU.dat result_CEU.dat

echo "treinando Mar..."
./svm_learn MAR.dat model_MAR.dat

echo "Classificando Mar..."
./svm_classify IMAGEM.dat model_MAR.dat result_MAR.dat


echo "treinando Montanha..."
./svm_learn MONTANHA.dat model_MONTANHA.dat

echo "Classificando Montanha..."
./svm_classify IMAGEM.dat model_MONTANHA.dat result_MONTANHA.dat

