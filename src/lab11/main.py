from graphviz import Digraph


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
    for sign in sorted(alphabet):
        stacks[sign] = []

    index = len(word)
    for char in word[::-1]:
        for sign in alphabet:
            if sign == char:
                stacks[char].append((char, str(index)))
                index -= 1
            elif (char, sign) not in independent_relations:
                stacks[sign].append('*')
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
    for char in raw:
        for sign in result[last_set_index]:
            if (char[0], sign[0]) not in I:
                result.append(raw)
                return result

    # can connect two sets because they are independent
    for char in raw:
        result[last_set_index].append(char)

    result[last_set_index].sort()


def draw_graph(result, independent_relations, word):
    dot = Digraph(comment='Graph')

    for i, sign in enumerate(word):
        dot.node(str(i + 1), sign)

    all_edges = []
    edges = []
    for i in range(len(result) - 2, -1, -1):
        for u in result[i]:
            for j in range(i + 1, len(result)):
                for v in result[j]:
                    if (u[0], v[0]) not in independent_relations and (u, v) not in all_edges:
                        add_edges(all_edges, u, v)
                        edges.append((u, v))
                        dot.edge(u[1], v[1])
    print(dot.source)
    return edges


def add_edges(all_edges, u, v):
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
    return FNF_from_graph_and_nodes(graph, nodes)


def get_nodes_from_graph(graph):
    nodes = []

    for i in range(len(graph)):
        u = graph[i]
        if u[0] not in nodes:
            nodes.append(u[0])
        if u[1] not in nodes:
            nodes.append(u[1])
    nodes = sorted(nodes, key=lambda x: x[1])
    return nodes


def add_transitive_edges(graph, nodes):
    for node in nodes[::-1]:
        for edge in graph:
            if node == edge[0]:
                for edge2 in graph:
                    if edge != edge2 and edge[1] == edge2[0] and (node, edge2[1]) not in graph:
                        graph.append((node, edge2[1]))

    graph = sorted(graph, key=lambda x: x[0][1])
    return graph


def FNF_from_graph_and_nodes(graph, nodes):
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


# A = {'a', 'b', 'c', 'd'}
# I = {('a', 'd'), ('d', 'a'), ('b', 'c'), ('c', 'b')}
# w = 'baadcb'

A = {'a', 'b', 'c', 'd', 'e', 'f'}
I = {('a', 'd'), ('d', 'a'), ('b', 'e'), ('e', 'b'), ('c', 'd'), ('d', 'c'), ('c', 'f'), ('f', 'c')}
w = 'acdcfbbe'

result_to_draw_graph, fnf_result = FNF(A, I, w)
print(result_to_draw_graph)
print(fnf_result)
graph_result = draw_graph(result_to_draw_graph, I, w)
print(FNF_from_graph(graph_result))
