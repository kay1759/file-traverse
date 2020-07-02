(ns file-traverse.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [file-traverse.core :as trv]))

(deftest traverse-test
  (testing "traverse on top of the project directory"
    (let [files (atom [])
          files_2 (atom [])
          files_3 (atom [])]
      (trv/traverse "." #(reset! files
                                 (conj @files (.getName %))))
      (trv/traverse "." #(reset! files_2
                                 (conj @files_2 (.getName %)))
                    #(not (str/starts-with? (.getName %) "."))
                    (fn [x] true))
      (trv/traverse "." #(reset! files_3
                                 (conj @files_3 (.getName %)))
                    (fn [x] true)
                    #(not= (.getName %) "src"))
      (is (some #(= % "LICENSE") @files))
      (is (some #(= % ".gitignore") @files))
      (is (some #(= % "core.clj") @files))
      (is (some #(= % "LICENSE") @files_2))
      (is (not (some #(= % ".gitignore") @files_2)))
      (is (some #(= % "core.clj") @files_2))
      (is (some #(= % "LICENSE") @files_3))
      (is (some #(= % ".gitignore") @files_3))
      (is (not (some #(= % "core.clj") @files_3))))))

(deftest traverse-content-test
  (testing "traverse on top of the project directory"
    (let [content (atom "")]
      (with-redefs [spit #(reset! content %2)]
        (trv/traverse "." #(spit % (slurp %))
                      #(= (.getName %) "core.clj"))
        (is (= (slurp "./src/file_traverse/core.clj") @content))
        (is (str/includes? @content "ltraverse"))
        (is (not (str/includes? @content "converted-traverse")))
        (trv/traverse "." #(spit % (str/replace (slurp %) #"ltraverse" "converted-traverse"))
                      #(= (.getName %) "core.clj"))
        (is (not (= (slurp "./src/file_traverse/core.clj") @content)))
        (is (not (str/includes? @content "ltraverse")))
        (is (str/includes? @content "converted-traverse"))))))

(deftest ltraverse-test
  (testing "ltraverse on top of the project directory"
    (let [files (atom [])]
      (trv/ltraverse "." #(reset! files
                                 (conj @files (.getName %))))
      (is (some #(= % "LICENSE") @files))
      (is (some #(= % ".gitignore") @files))
      (is (not (some #(= % "core.clj") @files))))))
