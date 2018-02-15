package fr.soat.spring5.step1;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

public class A_ReactorTest {

    @Test
    public void A_just() {
        // TODO: créer Flux avec les évenements 1, 2, 3
        Flux<Integer> result=Flux.fromArray(new Integer[]{1, 2, 3});
        StepVerifier.create(result)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    public void B_filter() {
        // TODO: prendre uniquement les nombres pair
        Flux<Integer> result = Flux.range(0, 7).filter(i ->  i.intValue()%2==0);
        StepVerifier.create(result)
                .expectNext(0, 2, 4, 6)
                .verifyComplete();
    }


    @Test
    public void C_map() {
        // TODO: créer Flux avec les évenements 1, 2, 3
        // puis les convertir en chaine de caractère
        Flux<String> result = Flux.empty().range(1,3).map(integer -> integer.toString());
        StepVerifier.create(result)
                .expectNext("1", "2", "3")
                .verifyComplete();
    }

    @Test
    public void D_merge() {
        // TODO: faire un merge de 2 flux différents en un seul flux
        // puis les convertir en chaine de caractère

        Flux<Integer> a = Flux.just(1, 2, 3);
        Flux<Integer> b = Flux.just(4, 5, 6);

        Flux<Integer> result = Flux.empty().concat(a,b);

        StepVerifier.create(result)
                .expectNext(1, 2, 3, 4, 5, 6)
                .verifyComplete();


    }

    @Test
    public void E_zip() {
        // TODO: composer de 2 flux différents en un seul flux
        // puis les convertir en chaine de caractère

        Flux<String> firstname = Flux.just("John", "Sarah", "Bob", "Patrick");
        Flux<String> name = Flux.just("Doe", "Conor", "L'Eponge", "L'étoile");

        Flux<String> result = Flux.zip(firstname,name, (f, n) -> f +" "+ n);

        StepVerifier.create(result)
                .expectNext("John Doe", "Sarah Conor", "Bob L'Eponge", "Patrick L'étoile")
                .verifyComplete();
    }

    @Test
    public void F_flatMap() {

        // TODO: créer un flux qui génère de nouvelles valeurs via l'opérateur flatMap
        Flux<Integer> source = Flux.just(1, 2, 3, 4);

        Flux<String> result = source.flatMap(
                new Function<Integer, Publisher<? extends String>>() {
                    @Override
                    public Publisher<? extends String> apply(Integer integer) {
                        if (integer==1) return Flux.just("a");
                        if (integer==2) return Flux.just("b","b");
                        if (integer==3) return Flux.just("c","c","c");
                        if (integer==4) return Flux.just("d","d","d","d");
                        return null;
                    }
                }
        );

        StepVerifier.create(result)
                .expectNext("a", "b", "b", "c", "c", "c", "d", "d", "d", "d")
                .verifyComplete();

    }

}
