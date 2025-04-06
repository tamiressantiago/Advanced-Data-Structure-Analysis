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
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import reader.GeneralFileReader;
import structure.BTree;

@State(Scope.Thread)
@Fork(value = 5)
@Warmup(iterations = 5, time = 5)
@Measurement(iterations = 10, time =5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)

public class BtreeBenchmark{
    private List<Integer> values;
    private BTree bTree;
    private BTree bTreeCheia;

    @Setup(Level.Iteration) //Feito a cada iteração
    public void setup() {
        values = GeneralFileReader.readValues("data/dados_sem_repeticoes_ordenados.csv");
        bTree = new BTree(256);
        bTreeCheia = new BTree(256);

	    for (int value : values) {
            bTreeCheia.Insert(value);
        }
    }

    @Benchmark
    public void baselineAccess(Blackhole blackhole) {
        blackhole.consume(bTree.isEmpty());
    }
    
    @Benchmark
    public void bTreeInsertAll(Blackhole blackhole) {
        for (int value : values) {
            bTree.Insert(value);
        }
    }

    @Benchmark
    public void bTreeInsertOneElement(Blackhole blackhole){
        Random random = new Random();
        int random_index = random.nextInt(100000000);
        bTreeCheia.Insert(random_index);
    }

    @Benchmark
    public void bTreeSearchAll(Blackhole blackhole) {
        for (int value : values) {
            blackhole.consume(bTreeCheia.Contain(value));
        }
    }

    @Benchmark
    public void bTreeSearchOneElement(Blackhole blackhole) {
        Random random = new Random();
        int random_index = random.nextInt(100000000);
        blackhole.consume(bTreeCheia.Contain(random_index));
    }
    
    @Benchmark
    public void bTreeRemoveAll(Blackhole blackhole) {        
        for (int value : values) {
            bTreeCheia.Remove(value); 
        }
    }

    @Benchmark
    public void bTreeRemoveOneElement(Blackhole blackhole) {
        Random random = new Random();
        int random_index = random.nextInt(100000000);
        bTreeCheia.Remove(random_index);
    }
}
