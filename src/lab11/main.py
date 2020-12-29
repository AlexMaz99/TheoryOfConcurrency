from graphviz import Digraph


def dependent_relations(alphabet, independent_relations):
    D = set()
    for x in sorted(alphabet):
        for y in sorted(alphabet):
            if (x, y) not in independent_relations and (x, y) not in D:
                D.add((x, y))
    return D


def trace(word):
    trace = '[<'

    for i in range(len(word)):
        trace += word[i]
        if i != len(word) - 1:
            trace += ', '
    trace += '>]'
    return trace


def FNF(alphabet, independent_relations, word):
    stacks = make_stacks(alphabet, independent_relations, word)
    max_len = len_of_biggest_stack(stacks, alphabet)
    result = []

    for i in range(max_len):
        letters = get_letters_from_top_of_the_stacks(stacks)

        if letters:
            if len(result) > 0:
                optimize_result(result, letters)
            else:
                result.append(letters)

    return result, FNF_to_string(result)


def make_stacks(alphabet, independent_relations, word):
    stacks = {}

    # make stack for each letter of the alphabet
    for letter in sorted(alphabet):
        stacks[letter] = []

    # index needed to distinguish between letters in the word
    index = len(word)

    # scan word from right to left
    for char in word[::-1]:
        # check relation with each letter of the alphabet
        for letter in alphabet:
            # push processed char on the stack with unique number
            if letter == char:
                stacks[char].append((char, str(index)))
                index -= 1
            # if char is dependent from letter then push '*' on the stack
            elif (char, letter) not in independent_relations:
                stacks[letter].append('*')

    return stacks


def len_of_biggest_stack(stacks, alphabet):
    max_len = 0
    for sign in alphabet:
        if len(stacks[sign]) > max_len:
            max_len = len(stacks[sign])
    return max_len


def get_letters_from_top_of_the_stacks(stacks):
    letters = []
    for sign in sorted(A):
        if stacks[sign]:
            char = stacks[sign].pop(len(stacks[sign]) - 1)
            if char != '*':
                letters.append(char)
    letters.sort()
    return letters


def optimize_result(result, raw):
    last_set_index = len(result) - 1

    # check if two adjacent sets can be joined
    for char in raw:
        for sign in result[last_set_index]:
            if (char[0], sign[0]) not in I:
                result.append(raw)
                return result

    # can connect two sets because they are independent
    for char in raw:
        result[last_set_index].append(char)

    result[last_set_index].sort()


def draw_graph(fnf, independent_relations, word):
    dot = Digraph()

    for i, sign in enumerate(word):
        dot.node(str(i + 1), sign)

    # to check if two nodes are connected indirectly
    all_edges = []
    edges = []
    for i in range(len(fnf) - 2, -1, -1):
        for u in fnf[i]:
            for j in range(i + 1, len(fnf)):
                for v in fnf[j]:
                    # if two nodes are dependent and they aren't connected directly or indirectly
                    if (u[0], v[0]) not in independent_relations and (u, v) not in all_edges:
                        add_edges(all_edges, u, v)
                        edges.append((u, v))
                        dot.edge(u[1], v[1])
    print(dot.source)
    return edges


def add_edges(all_edges, u, v):
    # add all transitive edges
    all_edges.append((u, v))
    for edge in all_edges:
        if edge[0] == v:
            all_edges.append((u, edge[1]))


def FNF_to_string(result):
    fnf = ''
    for collection in result:
        text = '('
        for sign in collection:
            text += sign[0]
        text += ')'
        fnf += text
    return fnf


def FNF_from_graph(graph):
    nodes = get_nodes_from_graph(graph)
    graph = add_transitive_edges(graph, nodes)

    result = ''
    processed_nodes = []
    for i in range(len(nodes)):
        u = nodes[i]
        if u not in processed_nodes:
            processed_nodes.append(u)
            result += '('
            result += u[0]
            for j in range(i + 1, len(nodes)):
                v = nodes[j]
                if (u, v) not in graph and v not in processed_nodes:
                    result += v[0]
                    processed_nodes.append(v)
            result += ')'

    return result


def get_nodes_from_graph(graph):
    nodes = []

    for i in range(len(graph)):
        u = graph[i]
        if u[0] not in nodes:
            nodes.append(u[0])
        if u[1] not in nodes:
            nodes.append(u[1])
    return sorted(nodes, key=lambda x: x[1])


def add_transitive_edges(graph, nodes):
    for node in nodes[::-1]:
        for u in graph:
            if node == u[0]:
                for v in graph:
                    if u != v and u[1] == v[0] and (node, v[1]) not in graph:
                        graph.append((node, v[1]))

    return sorted(graph, key=lambda x: x[0][1])


def main(alphabet, independent_relations, word):
    print("Alfabet A: ", sorted(alphabet))
    print("Relacja niezależności I: ", sorted(independent_relations, key=lambda x: x[0]))
    print("Relacja zależności D: ", sorted(dependent_relations(alphabet, independent_relations), key=lambda x: x[0]))
    print("Słowo w: ", word)
    print("Ślad słowa [w]: ", trace(word))
    result_to_draw_graph, fnf_result = FNF(alphabet, independent_relations, word)
    print("Postać normalna Foaty: ", fnf_result)
    print("Graf zależności dla słowa w:")
    graph_result = draw_graph(result_to_draw_graph, independent_relations, word)
    print("Postać normalna Foaty na podstawie grafu: ", FNF_from_graph(graph_result), "\n")


# example 1
A = {'a', 'b', 'c', 'd'}
I = {('a', 'd'), ('d', 'a'), ('b', 'c'), ('c', 'b')}
w = 'baadcb'
main(A, I, w)

# example 2
A = {'a', 'b', 'c', 'd', 'e', 'f'}
I = {('a', 'd'), ('d', 'a'), ('b', 'e'), ('e', 'b'), ('c', 'd'), ('d', 'c'), ('c', 'f'), ('f', 'c')}
w = 'acdcfbbe'
main(A, I, w)
