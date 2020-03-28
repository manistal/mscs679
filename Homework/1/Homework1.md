# Homework 1

## 1. Character length of using ​length
```scala
scala> quote.length
res0: Int = 205
```

## 2. Character length using ​foldLeft
```scala
scala> quote.foldLeft(0) { (length, entry) => length + 1 }
res1: Int = 205
```

## 3. Character length using ​count​.
```scala
scala> quote.count { (entry) => true } 
res2: Int = 205
```

## 4.Number of words. ​Hint:​ count the spaces.
```scala
scala> quote.count { (entry) => entry.isWhitespace } + 1 /* Always 1 less space than words in English */
res3: Int = 39
```

## 5.Number of punctuation marks, “?!.,~”. ​Hint:​ use ​count​.
```scala
scala> quote.count { (entry) => "?!.,~".contains(entry) }
res6: Int = 8
```

## 6.Number of non-capitalized words. ​Hint:​ split along spaces to get a new collection.
```scala
scala> quote.split(" ").count { (entry) => !entry.replaceAll("[?!.,~]", "").head.isUpper } 
res11: Int = 35
```

## 7.Capitalized words (i.e., words with the first letter being uppercase). ​Hint:​ use split resultsfrom prior problem.
```scala
scala> quote.split(" ").count { (entry) => entry.replaceAll("[?!.,~]", "").head.isUpper } 
res12: Int = 5
```

## 8.Encrypted words (not spaces) using a +1 Caesar cipher. Namely, a character, ​c​, is mapped toanother character, (​c​+1). Thus, “cat” → “dbu”. Use a double-nested ​map​.
```scala
scala> quote.split(" ").map { (word) => word.map { letter => ('a' to 'z').apply(('a' to 'z').indexOf(letter.toLower)+1) } }.mkString(" ")

res5: String = jg b dbu dbo ljmm b sbu jo b njovufa ipx mpoh xpvme ju cf ljmmjoh aaaaaa sbuta bia ipx mpoh joeffea nz qfstpobm pqjojpo jt uibu uif sbut xpvme ljmm uif dbua amfxjt dbsspmma po uif bewboubhft pg qbsbmmfmjtn
```

## 9.Repeat #8 except use a comprehension.
```scala
scala> for { word <- quote.split(" "); letter <- word } yield { ('a' to 'z').apply(('a' to 'z').indexOf(letter.toLower)+1) }
res9: Array[Char] = Array(j, g, b, d, b, u, d, b, o, l, j, m, m, b, s, b, u, j, o, b, n, j, o, v, u, f, a, i, p, x, m, p, o, h, x, p, v, m, e, j, u, c, f, l, j, m, m, j, o, h, a, a, a, a, a, a, s, b, u, t, a, b, i, a, i, p, x, m, p, o, h, j, o, e, f, f, e, a, n, z, q, f, s, t, p, o, b, m, p, q, j, o, j, p, o, j, t, u, i, b, u, u, i, f, s, b, u, t, x, p, v, m, e, l, j, m, m, u, i, f, d, b, u, a, a, m, f, x, j, t, d, b, s, s, p, m, m, a, p, o, u, i, f, b, e, w, b, o, u, b, h, f, t, p, g, q, b, s, b, m, m, f, m, j, t, n)
```

## 10.Case insensitive, highest to lowest count of each character. Hint: ​foldLeft​ with a ​Map​. (Here’sa ​cookbook​ for using immutable maps).
```scala
scala> quote.foldLeft(scala.collection.mutable.Map[Char, Int]()) { (count_map, character) => count_map += (character.toLower -> (count_map.getOrElse(character.toLower, 0) + 1) ) }.toSeq.sortWith(_._2 > _._2).foreach(println)
( ,39)
(a,18)
(l,17)
(i,14)
(t,13)
(n,12)
(o,12)
(e,11)
(h,7)
(r,7)
(s,7)
(d,5)
(w,5)
(c,4)
(g,4)
(,,4)
(0,4)
(k,3)
(m,3)
(p,3)
(u,3)
(f,2)
(!,1)
(b,1)
(.,1)
(6,1)
(v,1)
(y,1)
(~,1)
(?,1)
```

