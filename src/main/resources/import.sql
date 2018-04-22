
INSERT INTO public.role (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO public.role (id, name) VALUES (1, 'ROLE_ADMIN');

INSERT INTO public.user_e (id, email, first_name, last_name, password) VALUES (1, 'odubinskiy@ukr.net', 'Oleg', 'Dubinskiy', '$2a$10$edy4eJ.2H6ZALRyLwmlfzO.cCpXnqpU91fIOHRzchzPz.LshOAs4G');

INSERT INTO public.user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO public.user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO public.lab (id, heading, method_url, task) VALUES (0, 'Лабораторна робота 1', 'laba1', 'Відповідно до варіанту завдання розробити блок-схеми обчислення виразів для лінійного алгоритму, алгоритму, що розгалужується та циклічного алгоритму. У відповідності до блок-схеми створити програму обчислення виразу алгоритмічною мовою, узгодженою з викладачем. ');
INSERT INTO public.lab (id, heading, method_url, task) VALUES (1, 'Лабораторна робота 2', 'laba2', 'Використовуючи відповідний до варіанту алгоритм сортування написати програму сортування масиву даних. Застосовуючи дану програму, дослідити часову складність алгоритму сортування та порівняти її з теоретичною алгоритмічною складністю.');
INSERT INTO public.lab (id, heading, method_url, task) VALUES (2, 'Лабораторна робота 3', 'laba3', 'Використовуючи відповідний до варіанту алгоритм сортування написати програму сортування масиву даних. Застосовуючи дану програму, дослідити часову складність алгоритму сортування та порівняти її з теоретичною алгоритмічною складністю.');
INSERT INTO public.lab (id, heading, method_url, task) VALUES (3, 'Лабораторна робота 4', 'laba4', 'Використовуючи відповідний до варіанту алгоритм сортування написати програму сортування масиву даних. Застосовуючи дану програму, дослідити часову складність алгоритму сортування та порівняти її з теоретичною алгоритмічною складністю.');
INSERT INTO public.lab (id, heading, method_url, task) VALUES (4, 'Лабораторна робота 5', 'laba5', 'Використовуючи відповідний до варіанту алгоритм сортування написати програму сортування масиву даних. Застосовуючи дану програму, дослідити часову складність алгоритму сортування та порівняти її з теоретичною алгоритмічною складністю.');


INSERT INTO public.lab_data (id, value, name) VALUES (0, 1, 'a');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 2, 'b');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 11, 'c');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 5, 'd');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 7, 'l');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 2, 'k');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 8, 'w');
INSERT INTO public.lab_data (id, value, name) VALUES (0, 12, 'f');

