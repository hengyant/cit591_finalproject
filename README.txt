/**
 * 
 * @author HengyanTao
 *
 */
// hengyan's design
for the design change, I would like make a separate class to get the dictionary words;
a separate class for all the helpers functions like prettyPrint, getNumeratorset, getDenominatorset,
getcommonPercent, getSimilarityMetric, etc;

These two separate classes can be re-used for any other classed for extensibility

and keep WordRecommender class only have purpose to getWordSuggestions per Users's inputs.