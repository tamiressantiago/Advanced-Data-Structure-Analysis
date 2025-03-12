/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import reader.GeneralFileReader;
import structure.SplayTree;

@State(Scope.Benchmark)
@Fork(value = 5)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MyBenchmark {

	private SplayTree<Integer> tree;
    private List<Integer> values;

    @Setup // Executa antes de cada conjunto de execuções do benchmark
    public void setup() {
        values = GeneralFileReader.readValues("data/dados_com_repeticoes_desordenados.csv");
        splayTree = new SplayTree<>();
	splayTreeCheia = new SplayTree<>();
	    for (int value : values) {
            splayTree.insert(value);
        }
    }

    @Benchmark
    public void baselineAccess(Blackhole blackhole) {
        blackhole.consume(splayTree.isEmpty());
    }

    @Benchmark
    public void benchmarkInsertAll() {
        for (int value : values) {
            splayTree.insert(value);
        }
    }

    @Benchmark
    public void benchmarkSearchAll(Blackhole blackhole, SplayTree splayTreeCheia) {

        //buscar e blackhole consome o resultado
        for (int value : values) {
            blackhole.consume(splayTreeCheia.contains(value));
        }
    }
    
    
    @Benchmark
    public void benchmarkRemoveAll(SplayTree splayTreeCheia) {
        SplayTree<Integer> tree = new SplayTree<>();
        
        for (int value : values) {
            splayTreeCheia.remove(value); 
        }
    }
}


